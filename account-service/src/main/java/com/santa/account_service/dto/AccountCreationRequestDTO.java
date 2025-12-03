package com.santa.account_service.dto;

import lombok.Data;


@Data
public class AccountCreationRequestDTO {
    private String userId;
    private String bankName;
    private String accountType;
    private double balance;
}
