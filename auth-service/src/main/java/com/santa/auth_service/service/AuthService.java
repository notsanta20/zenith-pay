package com.santa.auth_service.service;

import com.santa.auth_service.dto.RegisterRequestDTO;
import com.santa.auth_service.dto.RegisterResponseDTO;
import com.santa.auth_service.model.User;
import com.santa.auth_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private UserRepo userRepo;

    @Autowired
    public AuthService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public RegisterResponseDTO registerUser(RegisterRequestDTO req) {
        User user = User.builder()
                .email(req.getEmail())
                .passwordHash(req.getPassword())
                .isActive(false)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        User createdUser = userRepo.save(user);

        return new RegisterResponseDTO(createdUser.getId().toString(), "User created successfully");
    }
}
