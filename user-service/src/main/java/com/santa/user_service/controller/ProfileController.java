package com.santa.user_service.controller;

import com.santa.user_service.dto.*;
import com.santa.user_service.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileDTO> getProfile(@RequestHeader("userId") String userId) {
        ProfileDTO profile = profileService.getProfile(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileUpdateResponseDTO> updateProfile(@RequestBody ProfileUpdateRequestDTO req, @RequestHeader("userId") String userId) {
        ProfileUpdateResponseDTO updatedUser = profileService.updateUser(req, userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/user-status")
    public UserStatusDTO getUserStatus(@RequestHeader("userId") String userId) {
        return profileService.getUserStatus(userId);
    }

    @GetMapping("/username")
    public String getUsername(@RequestHeader("userId") String userId) {
        return profileService.getUsername(userId);
    }

    @PutMapping("/update-security-notification")
    public ResponseEntity<String> updateSecurityNotification(@RequestHeader("userId") String userId) {
        profileService.updateSecurityNotification(userId);
        return new ResponseEntity<>("Preference updated successfully", HttpStatus.OK);
    }

    @PutMapping("/update-general-notification")
    public ResponseEntity<String> updateGeneralNotification(@RequestHeader("userId") String userId) {
        profileService.updateGeneralNotification(userId);
        return new ResponseEntity<>("Preference updated successfully", HttpStatus.OK);
    }
}
