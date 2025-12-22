package com.santa.auth_service.controller;

import com.santa.auth_service.dto.*;
import com.santa.auth_service.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final boolean secureCookie;

    @Autowired
    public AuthController(AuthService authService, @Value("${secure.cookie}") boolean isSecure) {
        this.authService = authService;
        secureCookie = isSecure;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@RequestBody RegisterRequestDTO req) {
        RegisterResponseDTO res = authService.registerUser(req);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO req, HttpServletResponse response) {
        LoginRes auth = authService.loginUser(req);

        LoginResponseDTO res = LoginResponseDTO.builder()
                .message("Logged in successfully")
                .isActive(auth.isActive())
                .build();

        ResponseCookie authCookie = ResponseCookie.from("authToken", auth.getAccessToken())
                .httpOnly(true)
                .path("/")
                .maxAge(auth.getExpiry())
                .sameSite(SameSiteCookies.STRICT.toString())
                .secure(secureCookie)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, authCookie.toString());

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO req) {

        return new ResponseEntity<>(new RefreshTokenResponseDTO("asdf", "asdf", "1000"), HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyUserResponseDTO> verifyUser(@RequestHeader("userId") String userId) {
        VerifyUserResponseDTO res = authService.verifyUser(userId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/user/bootstrap")
    public ResponseEntity<UserBootstrapDTO> userBootstrap(@RequestHeader("userId") String userId) {
        UserBootstrapDTO res = authService.userBootstrap(userId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestHeader("userId") String userId, @RequestBody UpdatePasswordDTO req) {
        authService.updatePassword(userId,req);

        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {

        ResponseCookie authCookie = ResponseCookie.from("authToken", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite(SameSiteCookies.STRICT.toString())
                .secure(secureCookie)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, authCookie.toString());

        return new ResponseEntity<>("logged out successfully", HttpStatus.OK);
    }
}
