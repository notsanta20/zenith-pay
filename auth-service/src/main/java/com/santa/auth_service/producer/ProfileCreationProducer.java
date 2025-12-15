package com.santa.auth_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProfileCreationProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public ProfileCreationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createProfile(String userId){
        kafkaTemplate.send("create-profile",userId);
    }
}
