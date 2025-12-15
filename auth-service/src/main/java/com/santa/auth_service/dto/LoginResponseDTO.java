package com.santa.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String expiry;
    private String userId;
    private String email;
    private boolean isActive;
}
