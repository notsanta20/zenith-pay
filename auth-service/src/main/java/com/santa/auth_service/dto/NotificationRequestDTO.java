package com.santa.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequestDTO {
    private String userId;
    private String notificationType;
    private String message;
}
