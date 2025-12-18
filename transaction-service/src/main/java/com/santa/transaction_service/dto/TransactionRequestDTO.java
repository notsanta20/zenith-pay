package com.santa.transaction_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequestDTO {
    private String accountNumber;
    private double amount;
    private String transactionType;
}
