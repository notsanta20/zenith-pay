package com.santa.transaction_service.dto;

import lombok.Data;


@Data
public class TransactRequestDTO {
    private String fromAccountNumber;
    private String toAccountNumber;
    private double amount;
    private String remarks;
}
