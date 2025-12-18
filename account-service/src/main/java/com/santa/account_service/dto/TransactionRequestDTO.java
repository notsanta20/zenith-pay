package com.santa.account_service.dto;

import lombok.Data;

@Data
public class TransactionRequestDTO {
    private String accountNumber;
    private double amount;
    private String transactionType;
}
