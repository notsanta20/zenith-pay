package com.santa.user_service.producer;

import com.santa.user_service.dto.LogDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public LogProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createLog(LogDTO log) {
        kafkaTemplate.send("create-log", log);
    }
}
