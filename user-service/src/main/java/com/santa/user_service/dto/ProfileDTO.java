package com.santa.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class ProfileDTO {
    private String full_name;
    private LocalDate dob;
    private String phone;
}
