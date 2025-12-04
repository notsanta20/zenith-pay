package com.santa.account_service.consumer;

import com.santa.account_service.dto.TransactionDTO;
import com.santa.account_service.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

    private AccountRepo accountRepo;

    @Autowired
    public TransactionConsumer(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @KafkaListener(topics = "transaction-topic", groupId = "transaction-group")
    public void updateBalance(TransactionDTO transaction){
        System.out.println(transaction.toString());
    }
}
