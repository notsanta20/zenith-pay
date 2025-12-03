package com.santa.auth_service.service;

import com.santa.auth_service.dto.LoginRequestDTO;
import com.santa.auth_service.dto.LoginResponseDTO;
import com.santa.auth_service.dto.RegisterRequestDTO;
import com.santa.auth_service.dto.RegisterResponseDTO;
import com.santa.auth_service.exception.EmailAlreadyExistsException;
import com.santa.auth_service.exception.IncorrectPasswordException;
import com.santa.auth_service.exception.UserNotFoundException;
import com.santa.auth_service.model.User;
import com.santa.auth_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private UserRepo userRepo;

    @Autowired
    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public RegisterResponseDTO registerUser(RegisterRequestDTO req) {
        Optional<User> existingUser = userRepo.findByEmail(req.getEmail());

        if(existingUser.isPresent()) throw new EmailAlreadyExistsException(req.getEmail());

        User user = User.builder()
                .email(req.getEmail())
                .passwordHash(req.getPassword())
                .isActive(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User createdUser = userRepo.save(user);

        return new RegisterResponseDTO(createdUser.getId().toString(), "User created successfully");
    }

    public LoginResponseDTO loginUser(LoginRequestDTO req) {
        User user = userRepo.findByEmail(req.getEmail()).orElseThrow(()->new UserNotFoundException(req.getEmail()));

        if(!user.getPasswordHash().equals(req.getPassword())) throw new IncorrectPasswordException();

        LoginResponseDTO res = LoginResponseDTO.builder()
                .accessToken("asdf")
                .refreshToken("asdf")
                .expiry("1m")
                .userId(user.getId().toString())
                .email(user.getEmail())
                .build();

        return res;
    }
}
