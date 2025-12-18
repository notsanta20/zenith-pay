package com.santa.account_service.dto;

import com.santa.account_service.model.Account;
import com.santa.account_service.model.AccountStatus;
import com.santa.account_service.model.AccountType;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountResponseDTO {
    private UUID accountId;
    private String accountName;
    private String accountNumber;
    private String ifscCode;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private double balance;

    public AccountResponseDTO(Account account) {
        this.accountId = account.getAccountId();
        this.accountName = account.getBankName();
        this.accountNumber = account.getAccountNumber();
        this.ifscCode = account.getIfscCode();
        this.accountType = account.getAccountType();
        this.accountStatus = account.getAccountStatus();
        this.balance = account.getBalance();
    }
}
