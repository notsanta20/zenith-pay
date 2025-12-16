package com.santa.user_service.service;

import com.santa.user_service.dto.ProfileUpdateRequestDTO;
import com.santa.user_service.dto.ProfileUpdateResponseDTO;
import com.santa.user_service.exception.ProfileNotFoundException;
import com.santa.user_service.model.Profile;
import com.santa.user_service.producer.ActivateAccountProducer;
import com.santa.user_service.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepo profileRepo;
    private final ActivateAccountProducer activateAccountProducer;

    @Autowired
    public ProfileService(ProfileRepo profileRepo, ActivateAccountProducer activateAccountProducer){
        this.profileRepo = profileRepo;
        this.activateAccountProducer = activateAccountProducer;
    }

    public Profile getProfile(UUID id){
        return profileRepo.findById(id).orElseThrow(()->new ProfileNotFoundException(id.toString()));
    }

    public ProfileUpdateResponseDTO updateUser(ProfileUpdateRequestDTO req){
        Profile currentUser = getProfile(UUID.fromString(req.getUserId()));

        currentUser.setFull_name(req.getFullName());
        currentUser.setDob(LocalDate.parse(req.getDob()));
        currentUser.setPhone(req.getPhone());
        currentUser.setKyc_status(false);
        currentUser.setUpdated_at(LocalDateTime.now());

        profileRepo.save(currentUser);
        activateAccountProducer.activateAccount(req.getUserId());


        return new ProfileUpdateResponseDTO("Profile has been updated", currentUser.isKyc_status());
    }

    public boolean getKycStatus(String userId) {
        Profile profile = getProfile(UUID.fromString(userId));

        return profile.isKyc_status();
    }
}
