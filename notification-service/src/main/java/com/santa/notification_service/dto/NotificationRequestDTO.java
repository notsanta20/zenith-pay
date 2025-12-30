package com.santa.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NotificationRequestDTO {
    private String userId;
    private String notificationType;
    private String message;
}
