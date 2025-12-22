package com.santa.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "profiles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    private UUID user_id;
    private String full_name;
    private LocalDate dob;
    private String phone;
    private boolean kyc_status;
    private boolean securityNotifications;
    private boolean generalNotifications;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
