package com.santa.account_service.service;

import com.santa.account_service.dto.AccountCreationRequestDTO;
import com.santa.account_service.dto.AccountCreationResponseDTO;
import com.santa.account_service.model.Account;
import com.santa.account_service.model.AccountStatus;
import com.santa.account_service.model.AccountType;
import com.santa.account_service.repo.AccountRepo;
import com.santa.account_service.utlis.LongNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public AccountCreationResponseDTO createAccount(AccountCreationRequestDTO req) {
        String accountNumber = LongNumberGenerator.getID(12);
        String ifsc = LongNumberGenerator.getID(10);

        Account account = Account.builder()
                .userId(UUID.fromString(req.getUserId()))
                .bankName(req.getBankName())
                .accountNumber(accountNumber)
                .ifscCode(ifsc)
                .accountType(AccountType.valueOf(req.getAccountType()))
                .balance(req.getBalance())
                .accountStatus(AccountStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        accountRepo.save(account);

        return new AccountCreationResponseDTO(account);
    }
}
