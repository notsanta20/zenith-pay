package com.santa.account_service.advice;

import com.santa.account_service.dto.LogDTO;
import com.santa.account_service.exception.ErrorResponse;
import com.santa.account_service.exception.AccountNotFoundException;
import com.santa.account_service.exception.InsufficientBalanceException;
import com.santa.account_service.model.LogLevel;
import com.santa.account_service.model.LogServiceType;
import com.santa.account_service.producer.LogProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LogProducer logProducer;

    @Autowired
    public GlobalExceptionHandler(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(AccountNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.ACCOUNT)
                .message(ex.getMessage())
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.ACCOUNT)
                .message(ex.getMessage())
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred: " + ex.getMessage());

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.ACCOUNT)
                .message("internal server error: " + ex.getMessage())
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
