package com.santa.account_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProfileUpdateProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public ProfileUpdateProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void updateProfile(String userId){
        kafkaTemplate.send("update-profile",userId);
    }
}
