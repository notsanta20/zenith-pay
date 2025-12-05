package com.santa.account_service.dto;

import lombok.Data;

@Data
public class TransactionRequestDTO {
    private String accountId;
    private String txnId;
    private double amount;
    private String type;
}
