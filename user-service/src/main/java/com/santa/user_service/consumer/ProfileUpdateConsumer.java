package com.santa.user_service.consumer;

import com.santa.user_service.exception.ProfileNotFoundException;
import com.santa.user_service.model.Profile;
import com.santa.user_service.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProfileUpdateConsumer {

    private ProfileRepo profileRepo;

    @Autowired
    public ProfileUpdateConsumer(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    @KafkaListener(topics = "update-profile", groupId = "update-profile-group")
    public void updateProfile(String userId){
        Profile profile = profileRepo.findById(UUID.fromString(userId)).orElseThrow(()->new ProfileNotFoundException(userId));
        profile.setKyc_status(true);
        profileRepo.save(profile);
    }
}
