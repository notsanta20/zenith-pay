package com.santa.auth_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String message;
    private boolean isActive;
}
