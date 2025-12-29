package com.santa.notification_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogProducer {
    private final KafkaTemplate<String,String> kafkaTemplate;

    public LogProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void entryLog(String userId){
        kafkaTemplate.send("log-entry",userId);
    }
}
