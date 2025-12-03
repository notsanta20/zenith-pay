package com.santa.account_service.controller;

import com.santa.account_service.dto.AccountCreationRequestDTO;
import com.santa.account_service.dto.AccountCreationResponseDTO;
import com.santa.account_service.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountCreationResponseDTO> createAccount(@RequestBody AccountCreationRequestDTO req){
        AccountCreationResponseDTO res = accountService.createAccount(req);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
