package com.santa.transaction_service.repo;

import com.santa.transaction_service.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByAccountNumber(String accountId);

    @Query("SELECT t FROM Transaction t WHERE accountNumber in :accountNumbers ORDER BY t.timestamp DESC")
    List<Transaction> findAllByUserId(List<String> accountNumbers);
}
