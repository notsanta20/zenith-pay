package com.santa.transaction_service.dto;

import lombok.Data;


@Data
public class DepositRequestDTO {
    private String accountId;
    private String type;
    private double amount;
    private String reference;
    private String remarks;
}
