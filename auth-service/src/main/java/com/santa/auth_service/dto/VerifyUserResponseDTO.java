package com.santa.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyUserResponseDTO {
    private String userId;
    private String message;
    private boolean isVerified;
}
