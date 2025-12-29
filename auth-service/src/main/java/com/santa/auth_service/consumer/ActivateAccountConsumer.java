package com.santa.auth_service.consumer;

import com.santa.auth_service.dto.LogDTO;
import com.santa.auth_service.exception.UserNotFoundException;
import com.santa.auth_service.model.LogLevel;
import com.santa.auth_service.model.LogServiceType;
import com.santa.auth_service.model.User;
import com.santa.auth_service.producer.LogProducer;
import com.santa.auth_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ActivateAccountConsumer {

    private UserRepo userRepo;
    private final LogProducer logProducer;

    @Autowired
    public ActivateAccountConsumer(UserRepo userRepo, LogProducer logProducer) {
        this.userRepo = userRepo;
        this.logProducer = logProducer;
    }

    @KafkaListener(topics = "activate-account", groupId = "activate-account-group")
    public void activateAccount(String userId){
        User user = userRepo.findById(UUID.fromString(userId)).orElseThrow(()->new UserNotFoundException(userId));

        user.setActive(true);

        userRepo.save(user);

        LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.INFO)
                .serviceType(LogServiceType.AUTH)
                .message("user %s, activated.".formatted(userId))
                .build();

        logProducer.createLog(log);
    }
}
