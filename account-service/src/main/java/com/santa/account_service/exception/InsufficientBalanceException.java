package com.santa.account_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(double balance){
        super("Insufficient Account balance : " + balance);
    }
}