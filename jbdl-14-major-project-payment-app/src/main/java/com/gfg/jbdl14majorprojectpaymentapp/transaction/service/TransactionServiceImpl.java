package com.gfg.jbdl14majorprojectpaymentapp.transaction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.jbdl14majorprojectpaymentapp.exception.NotFoundException;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.Transaction;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.TransactionStatus;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.*;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TransactionServiceImpl  implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {
        Transaction transaction = Transaction.builder()
                .transactionAmount(transactionRequest.getTransactionAmount())
                .transactionType(transactionRequest.getTransactionType())
                .fromUser(transactionRequest.getFromUser())
                .toUser(transactionRequest.getToUser())
                .status(TransactionStatus.PENDING)
                .txId(UUID.randomUUID().toString())
                .createAt(new Date())
                .build();
        transaction = transactionRepository.save(transaction);
        TransactionEvent transactionEvent = TransactionEvent.builder()
                .transactionAmount(transaction.getTransactionAmount())
                .transactionId(transaction.getTxId())
                .transactionType(transaction.getTransactionType())
                .fromUser(transaction.getFromUser())
                .toUser(transaction.getToUser()).build();

        try {
            kafkaTemplate.send("transaction",objectMapper.writeValueAsString(transactionEvent));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return TransactionResponse.builder()
                .status(transaction.getStatus())
                .transactionAmount(transaction.getTransactionAmount())
                .transactionId(transaction.getTxId())
                .build();
    }

    @Override
    public TransactionResponse get(String txId) throws NotFoundException {
        Transaction transaction = transactionRepository.findByTxId(txId)
                .orElseThrow(()->new NotFoundException("transaction is not present"));

        return TransactionResponse.builder()
                .status(transaction.getStatus())
                .transactionAmount(transaction.getTransactionAmount())
                .transactionId(transaction.getTxId())
                .build();
    }

    @Override
    @KafkaListener(topics = "rollbackTx",groupId = "transaction")
    public void rollback(String rollbackRequest) {
        RollbackEvent rollbackEvent = null;
        try {
             rollbackEvent = objectMapper.readValue(rollbackRequest,RollbackEvent.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            Transaction transaction = transactionRepository.findByTxId(rollbackEvent.getTransactionId())
                    .orElseThrow(()->new NotFoundException("transaction is not present"));
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    @KafkaListener(topics = "wallet",groupId = "transaction")
    public void update(String updateRequest) {
        WalletEvent walletEvent = null;
        try {
            walletEvent = objectMapper.readValue(updateRequest, WalletEvent.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            Transaction transaction = transactionRepository.findByTxId(walletEvent.getTransactionId())
                    .orElseThrow(()->new NotFoundException("transaction is not present"));
            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
