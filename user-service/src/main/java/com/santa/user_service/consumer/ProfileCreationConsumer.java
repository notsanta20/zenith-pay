package com.santa.user_service.consumer;

import com.santa.user_service.model.Profile;
import com.santa.user_service.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProfileCreationConsumer {

    private ProfileRepo profileRepo;

    @Autowired
    public ProfileCreationConsumer(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    @KafkaListener(topics = "new-topic", groupId = "profile-creation-group")
    public void createProfile(String userId){
        Profile profile = Profile.builder()
                .user_id(UUID.fromString(userId))
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        profileRepo.save(profile);
    }
}
