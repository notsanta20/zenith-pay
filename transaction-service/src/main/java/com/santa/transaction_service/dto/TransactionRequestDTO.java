package com.santa.transaction_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionRequestDTO {
    private String accountId;
    private String txnId;
    private double amount;
    private String type;
}
