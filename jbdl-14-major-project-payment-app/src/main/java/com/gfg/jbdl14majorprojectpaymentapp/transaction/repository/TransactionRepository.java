package com.gfg.jbdl14majorprojectpaymentapp.transaction.repository;

import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Optional<Transaction> findByTxId(String transactionId);
}
