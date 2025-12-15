package com.santa.account_service.controller;

import com.santa.account_service.dto.*;
import com.santa.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountCreationRequestDTO req){
        AccountResponseDTO res = accountService.createAccount(req);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> getAccount(@PathVariable String accountId){
        AccountResponseDTO res = accountService.getAccount(accountId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/total-balance")
    public ResponseEntity<Double> getTotalBalance(@PathVariable String userId){
        double res = accountService.getTotalBalance(userId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PatchMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccountStatus(
            @PathVariable String accountId,
            @RequestBody UpdateStatusRequestDTO req){
        AccountResponseDTO res = accountService.updateAccountStatus(accountId,req);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{accountId}/transact")
    public ResponseEntity<TransactionResponseDTO> updateAccountBalance(
            @PathVariable String accountId,
            @RequestBody TransactionRequestDTO req){
        TransactionResponseDTO res = accountService.updateAccountBalance(accountId,req);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AccountResponseDTO>> getAllAccounts(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<AccountResponseDTO> res = accountService.getAllAccounts(userId,page,size);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
