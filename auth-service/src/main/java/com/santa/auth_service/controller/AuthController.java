package com.santa.auth_service.controller;

import com.santa.auth_service.dto.RegisterRequestDTO;
import com.santa.auth_service.dto.RegisterResponseDTO;
import com.santa.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@RequestBody RegisterRequestDTO req){
        RegisterResponseDTO res = authService.registerUser(req);

        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }
}
