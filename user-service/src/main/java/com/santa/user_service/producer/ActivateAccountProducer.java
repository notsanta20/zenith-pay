package com.santa.user_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActivateAccountProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public ActivateAccountProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void activateAccount(String userId){
        kafkaTemplate.send("activate-account",userId);
    }
}
