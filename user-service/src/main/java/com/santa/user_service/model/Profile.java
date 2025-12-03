package com.santa.user_service.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "profiles")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String full_name;
    private Date dob;
    private String phone;
    private boolean kyc_status;
    private Date created_at;
    private Date updated_at;
}
