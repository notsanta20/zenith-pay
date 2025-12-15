package com.santa.auth_service.controller;

import com.santa.auth_service.dto.*;
import com.santa.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO req){
        LoginResponseDTO res = authService.loginUser(req);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO req){

        return new ResponseEntity<>(new RefreshTokenResponseDTO("asdf","asdf","1000"),HttpStatus.OK);
    }
}
