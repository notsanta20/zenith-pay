package com.santa.account_service.controller;

import com.santa.account_service.dto.*;
import com.santa.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountCreationRequestDTO req, @RequestHeader("userId") String userId){
        AccountResponseDTO res = accountService.createAccount(req, userId);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable String accountId){
        AccountResponseDTO res = accountService.getAccount(accountId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user/total-balance")
    public ResponseEntity<Double> getTotalBalance(@RequestHeader("userId") String userId){
        double res = accountService.getTotalBalance(userId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/update-status")
    public ResponseEntity<AccountResponseDTO> updateAccountStatus(
            @RequestBody UpdateStatusRequestDTO req){
        AccountResponseDTO res = accountService.updateAccountStatus(req);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/transact")
    public ResponseEntity<TransactionResponseDTO> updateAccountBalance(
            @RequestBody TransactionRequestDTO req){
        TransactionResponseDTO res = accountService.updateAccountBalance(req);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/all-accounts")
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(
            @RequestHeader("userId") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<AccountResponseDTO> res = accountService.getAllAccounts(userId,page,size);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/total-accounts")
    public int getTotalAccount(@RequestHeader("userId") String userId){
        return accountService.getTotalAccounts(userId);
    }

    @GetMapping("/all-account-numbers")
    public List<String> getAllAccountNumbers(@RequestHeader("userId") String userId){
        return accountService.getAllAccountNumbers(userId);
    }
}
