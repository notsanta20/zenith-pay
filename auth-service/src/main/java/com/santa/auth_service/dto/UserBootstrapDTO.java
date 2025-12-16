package com.santa.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBootstrapDTO {
    private boolean isActive;
    private boolean kycStatus;
    private int accountCount;
}
