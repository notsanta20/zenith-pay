package com.santa.account_service.service;

import com.santa.account_service.dto.*;
import com.santa.account_service.exception.AccountNotFoundException;
import com.santa.account_service.exception.InsufficientBalanceException;
import com.santa.account_service.model.Account;
import com.santa.account_service.model.AccountStatus;
import com.santa.account_service.model.AccountType;
import com.santa.account_service.repo.AccountRepo;
import com.santa.account_service.utlis.LongNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public AccountResponseDTO createAccount(AccountCreationRequestDTO req) {
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

        return new AccountResponseDTO(account);
    }

    public AccountResponseDTO getAccount(String id) {
        Account account = accountRepo.findById(UUID.fromString(id)).orElseThrow(()->new AccountNotFoundException(id));

        return new AccountResponseDTO(account);
    }

    public double getTotalBalance(String userId) {
        Pageable pageable = PageRequest.of(0,10);
        Page<Account> accounts = accountRepo.findAllByUserId(UUID.fromString(userId),pageable);

        if(!accounts.hasContent()){
            throw new AccountNotFoundException(userId);
        }

        double totalBalance = accounts.stream()
                        .map(a->a.getBalance())
                                .reduce(0.0,(a,e)->a+=e);

        return totalBalance;
    }


    public AccountResponseDTO updateAccountStatus(String accountId, UpdateStatusRequestDTO req) {
        Account account = accountRepo.findById(UUID.fromString(accountId)).orElseThrow(()->new AccountNotFoundException(accountId));

        account.setAccountStatus(AccountStatus.valueOf(req.getStatus()));
        accountRepo.save(account);

        return new AccountResponseDTO(account);
    }

    public TransactionResponseDTO updateAccountBalance(String accountId, TransactionRequestDTO req) {
        Account account = accountRepo.findById(UUID.fromString(accountId)).orElseThrow(()->new AccountNotFoundException(accountId));

        double currentBal = account.getBalance();

        if(req.getType().equals("DEBIT")){
            if(currentBal < req.getAmount()){
                throw new InsufficientBalanceException(currentBal);
            }

            account.setBalance(currentBal-req.getAmount());
        }
        else if(req.getType().equals("CREDIT")){
            account.setBalance(currentBal + req.getAmount());
        }

        accountRepo.save(account);

        return new TransactionResponseDTO("Success", account.getBalance());
    }

    public Page<AccountResponseDTO> getAllAccounts(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Account> accounts = accountRepo.findAllByUserId(UUID.fromString(userId),pageable);

        return accounts.map(AccountResponseDTO::new);
    }
}
