package com.santa.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProfileUpdateResponseDTO {
    private String message;
    private boolean kycStatus;
}
