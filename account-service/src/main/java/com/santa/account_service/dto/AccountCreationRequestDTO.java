package com.santa.account_service.dto;

import lombok.Data;


@Data
public class AccountCreationRequestDTO {
    private String accountName;
    private String accountType;
    private double balance;
}
