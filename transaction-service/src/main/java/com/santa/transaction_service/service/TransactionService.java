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
import java.util.List;

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
                .accountNumber(req.getAccountNumber())
                .type(TransactionType.valueOf(req.getTransactionType()))
                .amount(req.getAmount())
                .reference(req.getReference())
                .status(TransactionStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .remarks(req.getRemarks())
                .build();

        TransactionRequestDTO transactionRequestDTO = TransactionRequestDTO.builder()
                .accountNumber(req.getAccountNumber())
                .amount(transaction.getAmount())
                .transactionType(transaction.getType().toString())
                .build();

        ResponseEntity<TransactionResponseDTO> res = accountInterface.updateAccountBalance(transactionRequestDTO);

        if (res.getStatusCode().value() == 200) {
            transaction.setStatus(TransactionStatus.SUCCESS);
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
        }

        transactionRepo.save(transaction);

        return new DepositResponseDTO(transaction, res.getBody().getBalance());
    }

    public Page<Transaction> getAllTransactions(String accountNumber, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return transactionRepo.findAllByAccountNumber(accountNumber, pageable);
    }

    public TransactResponseDTO transact(TransactRequestDTO req) {
        DepositRequestDTO debitReq = DepositRequestDTO.builder()
                .accountNumber(req.getFromAccountNumber())
                .transactionType("DEBIT")
                .amount(req.getAmount())
                .reference("test transactions")
                .remarks("testing")
                .build();

        DepositRequestDTO creditReq = DepositRequestDTO.builder()
                .accountNumber(req.getToAccountNumber())
                .transactionType("CREDIT")
                .amount(req.getAmount())
                .reference("test transactions")
                .remarks("testing")
                .build();

        DepositResponseDTO debitRes = depositMoney(debitReq);
        depositMoney(creditReq);

        return new TransactResponseDTO(debitRes, req.getFromAccountNumber(), req.getToAccountNumber());
    }

    public List<Transaction> getAllUserTransactions(String userId, String limited) {
        List<Transaction> res;
        Pageable pageable = PageRequest.of(0, 10);
        List<String> accountNumbers = accountInterface.getAllAccountNumbers(userId);

        Page<Transaction> allTransactions = transactionRepo.findAllByUserId(accountNumbers, pageable);

        if(limited == null){
            res = allTransactions.stream()
                    .toList();
        }
        else{
            res = allTransactions.stream()
                    .limit(6)
                    .toList();
        }


        return res;
    }
}
