package com.santa.user_service.controller;

import com.santa.user_service.dto.ProfileUpdateRequestDTO;
import com.santa.user_service.dto.ProfileUpdateResponseDTO;
import com.santa.user_service.model.Profile;
import com.santa.user_service.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService){
        this.profileService = profileService;
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable String id){
        Profile profile = profileService.getProfile(UUID.fromString(id));
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileUpdateResponseDTO> updateProfile(@RequestBody ProfileUpdateRequestDTO req){
        ProfileUpdateResponseDTO updatedUser = profileService.updateUser(req);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
