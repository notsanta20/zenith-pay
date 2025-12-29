package com.santa.auth_service.advice;

import com.santa.auth_service.dto.LogDTO;
import com.santa.auth_service.exception.*;
import com.santa.auth_service.model.LogLevel;
import com.santa.auth_service.model.LogServiceType;
import com.santa.auth_service.producer.LogProducer;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final LogProducer logProducer;

    @Autowired
    public GlobalExceptionHandler(LogProducer logProducer) {
        this.logProducer = logProducer;
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(EmailAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.AUTH)
                .message(ex.getMessage())
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),"Invalid Email id or Password");

                LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.AUTH)
                .message("Invalid Email id or Password")
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorization(UnAuthorizedException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),ex.getMessage());

                LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.AUTH)
                .message(ex.getMessage())
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJwtToken(SignatureException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),"Invalid JWT token");

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.AUTH)
                .message("user provided invalid JWT token.")
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtToken(ExpiredJwtException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),"JWT token expired");

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.AUTH)
                .message("user JWT token expired.")
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<ErrorResponse> handleSamePassword(SamePasswordException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"new password and old password cannot be same");

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.AUTH)
                .message("failed to update password. new password and old password cannot be same.")
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"An error occurred: " + ex.getMessage());

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.WARN)
                .serviceType(LogServiceType.AUTH)
                .message("internal server error: " + ex.getMessage())
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
