package com.santa.user_service.service;

import com.santa.user_service.dto.ProfileUpdateRequestDTO;
import com.santa.user_service.dto.ProfileUpdateResponseDTO;
import com.santa.user_service.exception.ProfileNotFoundException;
import com.santa.user_service.model.Profile;
import com.santa.user_service.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ProfileService {

    private ProfileRepo profileRepo;

    @Autowired
    public ProfileService(ProfileRepo profileRepo){
        this.profileRepo = profileRepo;
    }

    public Profile getProfile(UUID id){
        return profileRepo.findById(id).orElseThrow(()->new ProfileNotFoundException(id.toString()));
    }

    public ProfileUpdateResponseDTO updateUser(ProfileUpdateRequestDTO req){
        Profile currentUser = getProfile(UUID.fromString(req.getUserId()));

        currentUser.setFull_name(req.getFullName());
        currentUser.setDob(LocalDate.parse(req.getDob()));
        currentUser.setPhone(req.getPhone());
        currentUser.setUpdated_at(LocalDate.now());

        return new ProfileUpdateResponseDTO("Profile has been updated");
    }
}
