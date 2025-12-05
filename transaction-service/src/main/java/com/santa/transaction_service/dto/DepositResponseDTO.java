package com.santa.transaction_service.dto;

import com.santa.transaction_service.model.Transaction;
import com.santa.transaction_service.model.TransactionStatus;
import com.santa.transaction_service.model.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DepositResponseDTO {
    private UUID txnId;
    private TransactionType type;
    private double amount;
    private double balance;
    private String reference;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String remarks;

    public DepositResponseDTO(Transaction transaction, double balance) {
        this.txnId = transaction.getTxnId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.balance = balance;
        this.reference = transaction.getReference();
        this.status = transaction.getStatus();
        this.timestamp = transaction.getTimestamp();
        this.remarks = transaction.getRemarks();
    }
}
