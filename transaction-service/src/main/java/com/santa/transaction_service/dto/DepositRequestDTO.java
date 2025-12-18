package com.santa.transaction_service.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class DepositRequestDTO {
    private String accountNumber;
    private String transactionType;
    private double amount;
    private String reference;
    private String remarks;
}
