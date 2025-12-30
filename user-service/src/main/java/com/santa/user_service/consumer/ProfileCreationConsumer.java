package com.santa.user_service.consumer;

import com.santa.user_service.dto.LogDTO;
import com.santa.user_service.model.LogLevel;
import com.santa.user_service.model.LogServiceType;
import com.santa.user_service.model.Profile;
import com.santa.user_service.producer.LogProducer;
import com.santa.user_service.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class ProfileCreationConsumer {

    private final ProfileRepo profileRepo;
    private final LogProducer logProducer;

    @Autowired
    public ProfileCreationConsumer(ProfileRepo profileRepo, LogProducer logProducer) {
        this.profileRepo = profileRepo;
        this.logProducer = logProducer;
    }

    @KafkaListener(topics = "create-profile", groupId = "create-profile-group")
    public void createProfile(String userId) {
        String userIdTrimmed = userId.replaceAll("\"", "");
        Profile profile = Profile.builder()
                .user_id(UUID.fromString(userIdTrimmed))
                .securityNotifications(true)
                .generalNotifications(true)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();

        profileRepo.save(profile);

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.INFO)
                .serviceType(LogServiceType.PROFILE)
                .message("user %s, profile created.".formatted(userId))
                .build();

        logProducer.createLog(log);
    }
}
