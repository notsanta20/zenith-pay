package com.santa.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SamePasswordException extends RuntimeException{
    public SamePasswordException(){
        super("Same Password");
    }
}