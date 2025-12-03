package com.santa.user_service.dto;

import lombok.Data;

@Data
public class ProfileUpdateRequestDTO {
    private String userId;
    private String fullName;
    private String dob;
    private String phone;

}
