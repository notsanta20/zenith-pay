package com.santa.user_service.repo;

import com.santa.user_service.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfileRepo extends JpaRepository<Profile, UUID> {
}
