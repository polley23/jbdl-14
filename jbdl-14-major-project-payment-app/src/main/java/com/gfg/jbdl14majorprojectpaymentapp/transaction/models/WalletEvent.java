package com.gfg.jbdl14majorprojectpaymentapp.transaction.models;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WalletEvent {
    private String fromUser;
    private String toUser;
    private Double transactionAmount;
    private String transactionId;
}
