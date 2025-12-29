package com.santa.user_service.advice;

import com.santa.user_service.dto.LogDTO;
import com.santa.user_service.exception.ErrorResponse;
import com.santa.user_service.exception.ProfileNotFoundException;
import com.santa.user_service.model.LogLevel;
import com.santa.user_service.model.LogServiceType;
import com.santa.user_service.producer.LogProducer;
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

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(ProfileNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.PROFILE)
                .message(ex.getMessage())
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred: " + ex.getMessage());

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.PROFILE)
                .message("internal server error: " + ex.getMessage())
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
