package com.santa.user_service.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "profiles")
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID user_id;
    private String full_name;
    private LocalDate dob;
    private String phone;
    private boolean kyc_status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
