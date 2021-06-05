package com.gfg.jbdl14majorprojectpaymentapp.transaction.entity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String txId;
    private String fromUser;
    private String toUser;
    private TransactionType transactionType;
    private TransactionStatus status;
    private Double transactionAmount;
    @Temporal(value = TemporalType.DATE)
    private Date createAt;
}
