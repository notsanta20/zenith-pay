package com.santa.transaction_service.service;

import com.santa.transaction_service.dto.*;
import com.santa.transaction_service.feign.AccountInterface;
import com.santa.transaction_service.model.Transaction;
import com.santa.transaction_service.model.TransactionStatus;
import com.santa.transaction_service.model.TransactionType;
import com.santa.transaction_service.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final AccountInterface accountInterface;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo, AccountInterface accountInterface) {
        this.transactionRepo = transactionRepo;
        this.accountInterface = accountInterface;
    }

    public DepositResponseDTO depositMoney(DepositRequestDTO req) {
        Transaction transaction = Transaction.builder()
                .accountId(UUID.fromString(req.getAccountId()))
                .type(TransactionType.valueOf(req.getType()))
                .amount(req.getAmount())
                .reference(req.getReference())
                .status(TransactionStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .remarks(req.getRemarks())
                .build();

        transactionRepo.save(transaction);

        TransactionRequestDTO transactionRequestDTO = TransactionRequestDTO.builder()
                        .accountId(req.getAccountId())
                                .txnId(transaction.getTxnId().toString())
                                        .amount(transaction.getAmount())
                                                .type(transaction.getType().toString())
                                                        .build();

        ResponseEntity<TransactionResponseDTO> res = accountInterface.updateAccountBalance(
                req.getAccountId(), transactionRequestDTO);

        if(res.getStatusCode().value() == 200){
            transaction.setStatus(TransactionStatus.SUCCESS);
        }else{
            transaction.setStatus(TransactionStatus.FAILED);
        }

     return new DepositResponseDTO(transaction,res.getBody().getBalance());
    }

    public Page<Transaction> getAllTransactions(String accountId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        return transactionRepo.findAllByAccountId(UUID.fromString(accountId),pageable);
    }
}
