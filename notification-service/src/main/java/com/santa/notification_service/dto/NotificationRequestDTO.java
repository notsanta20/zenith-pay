package com.santa.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequestDTO {
    private String notificationType;
    private String title;
    private String message;
}
