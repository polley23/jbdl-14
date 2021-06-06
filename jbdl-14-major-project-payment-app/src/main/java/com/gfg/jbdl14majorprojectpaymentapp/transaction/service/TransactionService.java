package com.gfg.jbdl14majorprojectpaymentapp.transaction.service;

import com.gfg.jbdl14majorprojectpaymentapp.exception.NotFoundException;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.TransactionRequest;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.models.TransactionResponse;

public interface TransactionService {
    public TransactionResponse create(TransactionRequest transactionRequest);
    public TransactionResponse get(String txId) throws NotFoundException;
    public void rollback(String rollbackRequest);
    public void update(String updateRequest);

}
