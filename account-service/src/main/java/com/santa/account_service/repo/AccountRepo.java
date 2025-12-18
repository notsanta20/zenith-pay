package com.santa.account_service.repo;

import com.santa.account_service.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepo extends JpaRepository<Account, UUID> {
    Page<Account> findAllByUserId(UUID userId, Pageable pageable);

    Optional<Account> findByAccountNumber(String accountNumber);
}
