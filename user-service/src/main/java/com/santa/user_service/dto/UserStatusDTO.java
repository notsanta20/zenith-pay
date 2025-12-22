package com.santa.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatusDTO {
    private boolean kyc_status;
    private boolean securityNotifications;
    private boolean generalNotifications;
}
