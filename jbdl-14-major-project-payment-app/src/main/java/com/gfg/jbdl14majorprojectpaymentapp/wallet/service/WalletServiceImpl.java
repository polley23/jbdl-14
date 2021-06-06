package com.gfg.jbdl14majorprojectpaymentapp.wallet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.jbdl14majorprojectpaymentapp.exception.NotFoundException;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.RollbackEvent;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.TransactionEvent;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.WalletEvent;
import com.gfg.jbdl14majorprojectpaymentapp.user.model.SignUp;
import com.gfg.jbdl14majorprojectpaymentapp.wallet.entity.Wallet;
import com.gfg.jbdl14majorprojectpaymentapp.wallet.repository.WalletException;
import com.gfg.jbdl14majorprojectpaymentapp.wallet.repository.WalletRepository;
import com.gfg.jbdl14majorprojectpaymentapp.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @KafkaListener(topics = "user",groupId = "wallet")
    public void create(String event) {
        SignUp signUp = null;
        try {
            signUp = objectMapper.readValue(event, SignUp.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Wallet wallet = Wallet.builder()
                .amount(100D)
                .username(signUp.getUsername())
                .build();
        walletRepository.save(wallet);

    }

    @Override
    @KafkaListener(topics = "transaction",groupId = "wallet")
    public void update(String event) {
        try {
            TransactionEvent transactionEvent = objectMapper.readValue(event,TransactionEvent.class);

            try {
                Wallet fromWallet = walletRepository.findByUsername(transactionEvent.getFromUser())
                        .orElseThrow(()->new NotFoundException("transaction is not present"));
                Wallet toWallet = walletRepository.findByUsername(transactionEvent.getToUser())
                        .orElseThrow(()->new NotFoundException("transaction is not present"));
                switch (transactionEvent.getTransactionType()){
                    case DEBIT:
                        if(fromWallet.getAmount() < transactionEvent.getTransactionAmount()){
                            throw new WalletException("not sufficient balance");
                        }
                        fromWallet.setAmount(fromWallet.getAmount()-transactionEvent.getTransactionAmount());
                        toWallet.setAmount(toWallet.getAmount()+transactionEvent.getTransactionAmount());
                        break;
                    case CREDIT:
                        if(toWallet.getAmount() < transactionEvent.getTransactionAmount()){
                            throw new WalletException("not sufficient balance");
                        }
                        toWallet.setAmount(toWallet.getAmount()-transactionEvent.getTransactionAmount());
                        fromWallet.setAmount(fromWallet.getAmount()+transactionEvent.getTransactionAmount());

                }
                walletRepository.save(fromWallet);
                walletRepository.save(toWallet);
                WalletEvent walletEvent = WalletEvent.builder()
                        .fromUser(transactionEvent.getFromUser())
                        .toUser(transactionEvent.getToUser())
                        .transactionAmount(transactionEvent.getTransactionAmount())
                        .transactionId(transactionEvent.getTransactionId())
                        .build();
                kafkaTemplate.send("wallet",objectMapper.writeValueAsString(walletEvent));

            } catch (NotFoundException|WalletException e) {
                RollbackEvent rollbackEvent = RollbackEvent.builder()
                        .fromUser(transactionEvent.getFromUser())
                        .toUser(transactionEvent.getToUser())
                        .transactionAmount(transactionEvent.getTransactionAmount())
                        .transactionId(transactionEvent.getTransactionId())
                        .reason(e.getMessage()).build();
                kafkaTemplate.send("rollbackTx",objectMapper.writeValueAsString(rollbackEvent));
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
