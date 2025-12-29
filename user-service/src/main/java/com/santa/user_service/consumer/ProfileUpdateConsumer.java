package com.santa.user_service.consumer;

import com.santa.user_service.dto.LogDTO;
import com.santa.user_service.exception.ProfileNotFoundException;
import com.santa.user_service.model.LogLevel;
import com.santa.user_service.model.LogServiceType;
import com.santa.user_service.model.Profile;
import com.santa.user_service.producer.LogProducer;
import com.santa.user_service.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProfileUpdateConsumer {

    private final ProfileRepo profileRepo;
    private final LogProducer logProducer;

    @Autowired
    public ProfileUpdateConsumer(ProfileRepo profileRepo, LogProducer logProducer) {
        this.profileRepo = profileRepo;
        this.logProducer = logProducer;
    }

    @KafkaListener(topics = "update-profile", groupId = "update-profile-group")
    public void updateProfile(String userId) {
        Profile profile = profileRepo.findById(UUID.fromString(userId)).orElseThrow(() -> new ProfileNotFoundException(userId));
        profile.setKyc_status(true);
        profileRepo.save(profile);

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.INFO)
                .serviceType(LogServiceType.PROFILE)
                .message("user %s, kyc verified.".formatted(userId))
                .build();

        logProducer.createLog(log);
    }
}
