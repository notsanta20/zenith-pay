package com.santa.api_gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class JwtMissingException extends RuntimeException{
    public JwtMissingException(){
        super("JWT token missing");
    }
}