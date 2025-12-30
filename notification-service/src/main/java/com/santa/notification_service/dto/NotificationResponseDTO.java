package com.santa.notification_service.dto;

import com.santa.notification_service.model.Notification;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponseDTO {
    private String id;
    private String notificationType;
    private String message;
    private LocalDateTime timeStamp;

    public NotificationResponseDTO(Notification notification) {
        this.id = notification.getId();
        this.notificationType = notification.getNotificationType().toString();
        this.message = notification.getMessage();
        this.timeStamp = notification.getCreatedAt();
    }
}
