package com.gfg.jbdl14majorprojectpaymentapp.transaction.models;

import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.TransactionStatus;
import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.TransactionType;
import lombok.*;

import javax.persistence.Entity;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionRequest {
    private String fromUser;
    private String toUser;
    private TransactionType transactionType;
    private Double transactionAmount;
}
