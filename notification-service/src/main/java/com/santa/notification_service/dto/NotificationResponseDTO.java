package com.santa.notification_service.dto;

import com.santa.notification_service.model.Notification;
import lombok.Data;

@Data
public class NotificationResponseDTO {
    private String id;
    private String notificationType;
    private String title;
    private String message;

    public NotificationResponseDTO(Notification notification) {
        this.id = notification.getId();
        this.notificationType = notification.getNotificationType().toString();
        this.title = notification.getTitle();
        this.message = notification.getMessage();
    }
}
