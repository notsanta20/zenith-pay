package com.santa.user_service.service;

import com.santa.user_service.dto.*;
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

    private Profile getProfileFunc(String userId){
        return profileRepo.findById(UUID.fromString(userId)).orElseThrow(()->new ProfileNotFoundException(userId));
    }

    public ProfileDTO getProfile(String userId){
        Profile profile = getProfileFunc(userId);

        return new ProfileDTO(profile);
    }

    public ProfileUpdateResponseDTO updateUser(ProfileUpdateRequestDTO req,String userId){
        Profile currentUser = getProfileFunc(userId);

        currentUser.setFull_name(req.getFullName());
        currentUser.setDob(LocalDate.parse(req.getDob()));
        currentUser.setPhone(req.getPhone());
        currentUser.setKyc_status(false);
        currentUser.setUpdated_at(LocalDateTime.now());

        profileRepo.save(currentUser);
        activateAccountProducer.activateAccount(userId);


        return new ProfileUpdateResponseDTO("Profile has been updated", currentUser.isKyc_status());
    }

    public UserStatusDTO getUserStatus(String userId) {
        Profile profile = getProfileFunc(userId);

        return new UserStatusDTO(profile.isKyc_status(), profile.isSecurityNotifications(), profile.isGeneralNotifications());
    }

    public String getUsername(String userId) {
        Profile profile = getProfileFunc(userId);

        return profile.getFull_name();
    }

    public void updateSecurityNotification(String userId) {
        Profile user = getProfileFunc(userId);

        user.setSecurityNotifications(!user.isSecurityNotifications());

        profileRepo.save(user);
    }

    public void updateGeneralNotification(String userId) {
        Profile user = getProfileFunc(userId);

        user.setGeneralNotifications(!user.isGeneralNotifications());

        profileRepo.save(user);

    }
}
