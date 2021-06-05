package com.gfg.jbdl14majorprojectpaymentapp.transaction.service;

import com.gfg.jbdl14majorprojectpaymentapp.exception.NotFoundException;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.Transaction;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.TransactionStatus;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.TransactionRequest;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.TransactionResponse;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TransactionServiceImpl  implements TransactionService{
    @Autowired
    private TransactionRepository transactionRepository;

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
    public void rollback(String rollbackRequest) {

    }
}
