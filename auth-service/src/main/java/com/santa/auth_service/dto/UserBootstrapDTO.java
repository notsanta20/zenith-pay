package com.santa.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserBootstrapDTO {
    private boolean isActive;
    private boolean kycStatus;
    private boolean securityNotifications;
    private boolean generalNotifications;
    private int accountCount;
    private String username;
    private LocalDateTime lastLoginAt;
}
