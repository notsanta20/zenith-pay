package com.santa.auth_service.service;

import com.santa.auth_service.config.jwt.JwtService;
import com.santa.auth_service.dto.*;
import com.santa.auth_service.exception.EmailAlreadyExistsException;
import com.santa.auth_service.exception.UnAuthorizedException;
import com.santa.auth_service.exception.UserNotFoundException;
import com.santa.auth_service.feign.AccountInterface;
import com.santa.auth_service.feign.ProfileInterface;
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
import java.util.UUID;

@Service
public class AuthService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    private final UserRepo userRepo;
    private final ProfileCreationProducer profileCreationProducer;
    AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ProfileInterface profileInterface;
    private final AccountInterface accountInterface;

    @Autowired
    public AuthService(UserRepo userRepo,
                       ProfileCreationProducer profileCreationProducer,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       ProfileInterface profileInterface,
                       AccountInterface accountInterface) {
        this.userRepo = userRepo;
        this.profileCreationProducer = profileCreationProducer;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.profileInterface = profileInterface;
        this.accountInterface = accountInterface;
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

        return new RegisterResponseDTO("User created successfully");
    }

    public LoginRes loginUser(LoginRequestDTO req) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        if (authentication.isAuthenticated()) {
            User user = userRepo.findByEmail(req.getEmail()).orElseThrow(() -> new UserNotFoundException(req.getEmail()));

            String token = jwtService.generateToken(user.getEmail(), user.getId().toString());

            user.setLastLoginAt(LocalDateTime.now());

            userRepo.save(user);

            return LoginRes.builder()
                    .accessToken(token)
                    .refreshToken("asdf")
                    .expiry(1000 * 60 * 20)
                    .userId(user.getId().toString())
                    .email(user.getEmail())
                    .isActive(user.isActive())
                    .build();

        } else {
            throw new UnAuthorizedException();
        }

    }

    public VerifyUserResponseDTO verifyUser(String userID) {
        User user = userRepo.findById(UUID.fromString(userID)).orElseThrow(()->new UserNotFoundException(userID));

        return new VerifyUserResponseDTO("user is Authenticated", true);
    }

    public UserBootstrapDTO userBootstrap(String userId) {
        User user = userRepo.findById(UUID.fromString(userId)).orElseThrow(()->new UserNotFoundException(userId));
        boolean kycStatus = profileInterface.checkKycStatus(userId);
        int totalAccounts = accountInterface.getTotalAccount(userId);
        String username = profileInterface.getUsername(userId);


        return new UserBootstrapDTO(user.isActive(), kycStatus, totalAccounts, username, user.getLastLoginAt());
    }
}
