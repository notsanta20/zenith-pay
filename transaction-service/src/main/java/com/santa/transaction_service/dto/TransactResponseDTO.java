package com.santa.transaction_service.dto;

import com.santa.transaction_service.model.TransactionStatus;
import com.santa.transaction_service.model.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
public class TransactResponseDTO {
    private UUID txnId;
    private String fromAccountNumber;
    private String toAccountNumber;
    private TransactionType type;
    private double amount;
    private double balance;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String remarks;

    public TransactResponseDTO(DepositResponseDTO res, String fromAccountNumber, String toAccountNumber) {
        this.txnId = res.getTxnId();
        this.fromAccountNumber = fromAccountNumber;
        this.toAccountNumber = toAccountNumber;
        this.type = res.getType();
        this.amount = res.getAmount();
        this.balance = res.getBalance();
        this.status = res.getStatus();
        this.timestamp = LocalDateTime.now();
        this.remarks = res.getRemarks();
    }
}
