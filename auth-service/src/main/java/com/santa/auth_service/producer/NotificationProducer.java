package com.santa.auth_service.producer;

import com.santa.auth_service.dto.LogDTO;
import com.santa.auth_service.dto.NotificationRequestDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;

    public NotificationProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addNotification(NotificationRequestDTO notification){
        kafkaTemplate.send("add-notification", notification);
    }
}
