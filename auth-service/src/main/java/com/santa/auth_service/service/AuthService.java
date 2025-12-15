package com.santa.auth_service.service;

import com.santa.auth_service.config.jwt.JwtService;
import com.santa.auth_service.dto.*;
import com.santa.auth_service.exception.EmailAlreadyExistsException;
import com.santa.auth_service.exception.UnAuthorizedException;
import com.santa.auth_service.exception.UserNotFoundException;
import com.santa.auth_service.model.User;
import com.santa.auth_service.producer.ProfileCreationProducer;
import com.santa.auth_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private UserRepo userRepo;
    private ProfileCreationProducer profileCreationProducer;
    AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public AuthService(UserRepo userRepo,
                       ProfileCreationProducer profileCreationProducer,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepo = userRepo;
        this.profileCreationProducer = profileCreationProducer;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public RegisterResponseDTO registerUser(RegisterRequestDTO req) {
        Optional<User> existingUser = userRepo.findByEmail(req.getEmail());

        if (existingUser.isPresent()) throw new EmailAlreadyExistsException(req.getEmail());

        User user = User.builder()
                .email(req.getEmail())
                .passwordHash(encoder.encode(req.getPassword()))
                .isActive(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User createdUser = userRepo.save(user);
        profileCreationProducer.createProfile(createdUser.getId().toString());

        return new RegisterResponseDTO(createdUser.getId().toString(), "User created successfully");
    }

    public LoginRes loginUser(LoginRequestDTO req) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        if (authentication.isAuthenticated()) {
            User user = userRepo.findByEmail(req.getEmail()).orElseThrow(()->new UserNotFoundException(req.getEmail()));

            String token = jwtService.generateToken(user.getEmail());

            LoginRes res = LoginRes.builder()
                    .accessToken(token)
                    .refreshToken("asdf")
                    .expiry(1000*60*2)
                    .userId(user.getId().toString())
                    .email(user.getEmail())
                    .isActive(user.isActive())
                    .build();

            return res;
        } else {
            throw new UnAuthorizedException();
        }

    }
}
