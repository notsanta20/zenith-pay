package com.santa.account_service.dto;

import com.santa.account_service.model.Account;
import com.santa.account_service.model.AccountType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountCreationResponseDTO {
    private UUID accountId;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private AccountType accountType;
    private double balance;

    public AccountCreationResponseDTO(Account account) {
        this.accountId = account.getAccountId();
        this.bankName = account.getBankName();
        this.accountNumber = account.getAccountNumber();
        this.ifscCode = account.getIfscCode();
        this.accountType = account.getAccountType();
        this.balance = account.getBalance();
    }
}
