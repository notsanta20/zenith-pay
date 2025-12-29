package com.santa.notification_service.consumer;

import com.santa.notification_service.dto.NotificationRequestDTO;
import com.santa.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AddNotificationConsumer {

    private final NotificationService notificationService;

    @Autowired
    public AddNotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "add-notification", groupId = "add-notification-group")
    public void addNotification(String userId , NotificationRequestDTO req){
        notificationService.addNotification(userId, req);
    }
}
