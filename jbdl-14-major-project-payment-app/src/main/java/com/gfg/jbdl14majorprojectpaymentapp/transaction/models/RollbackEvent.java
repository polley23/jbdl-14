package com.gfg.jbdl14majorprojectpaymentapp.transaction.models;

import com.gfg.jbdl14majorprojectpaymentapp.transaction.entity.TransactionType;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RollbackEvent {
    private String fromUser;
    private String toUser;
    private Double transactionAmount;
    private String transactionId;
    private String reason;
}
