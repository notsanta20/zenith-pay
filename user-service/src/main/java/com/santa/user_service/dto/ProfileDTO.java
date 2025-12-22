package com.santa.user_service.dto;

import com.santa.user_service.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ProfileDTO {
    private String full_name;
    private LocalDate dob;
    private String phone;
    private boolean kyc_status;
    private LocalDateTime created_at;

    public ProfileDTO(Profile profile) {
        this.full_name = profile.getFull_name();
        this.dob = profile.getDob();
        this.phone = profile.getPhone();
        this.kyc_status = profile.isKyc_status();
        this.created_at = profile.getCreated_at();
    }
}
