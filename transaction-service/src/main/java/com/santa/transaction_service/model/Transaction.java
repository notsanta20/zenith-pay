package com.santa.transaction_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID txnId;
    private String accountNumber;
    private TransactionType type;
    private double amount;
    private String reference;
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String remarks;
}
