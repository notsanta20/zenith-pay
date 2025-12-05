package com.santa.transaction_service.dto;

import com.santa.transaction_service.model.TransactionType;
import lombok.Data;


@Data
public class DepositRequestDTO {
    private String accountId;
    private String type;
    private double amount;
    private String reference;
    private String remarks;
}
