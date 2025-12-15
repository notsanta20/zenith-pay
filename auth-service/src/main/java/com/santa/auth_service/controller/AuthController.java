package com.santa.auth_service.controller;

import com.santa.auth_service.dto.*;
import com.santa.auth_service.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO req, HttpServletResponse response){
        LoginRes auth = authService.loginUser(req);

        LoginResponseDTO res = LoginResponseDTO.builder()
                .userId(auth.getUserId())
                .email(auth.getEmail())
                .isActive(auth.isActive())
                .build();

        ResponseCookie authCookie = ResponseCookie.from("authToken", auth.getAccessToken())
                .httpOnly(true)
                .path("/")
                .maxAge(auth.getExpiry())
                .sameSite(SameSiteCookies.STRICT.toString())
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, authCookie.toString());

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO req){

        return new ResponseEntity<>(new RefreshTokenResponseDTO("asdf","asdf","1000"),HttpStatus.OK);
    }
}
