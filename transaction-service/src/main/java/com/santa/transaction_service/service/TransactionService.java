package com.santa.transaction_service.service;

import com.santa.transaction_service.dto.*;
import com.santa.transaction_service.exception.AccountNotFoundException;
import com.santa.transaction_service.exception.InsufficientBalanceException;
import com.santa.transaction_service.feign.AccountInterface;
import com.santa.transaction_service.model.Transaction;
import com.santa.transaction_service.model.TransactionStatus;
import com.santa.transaction_service.model.TransactionType;
import com.santa.transaction_service.repo.TransactionRepo;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
                .status(TransactionStatus.FAILED)
                .timestamp(LocalDateTime.now())
                .remarks(req.getRemarks())
                .build();

        transactionRepo.save(transaction);

        TransactionRequestDTO transactionRequestDTO = TransactionRequestDTO.builder()
                .accountNumber(req.getAccountNumber())
                .amount(transaction.getAmount())
                .transactionType(transaction.getType().toString())
                .build();

        double balance = 0.0;
        try {
            ResponseEntity<TransactionResponseDTO> res = accountInterface.updateAccountBalance(transactionRequestDTO);
            balance = res.getBody().getBalance();

            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepo.save(transaction);
            return new DepositResponseDTO(transaction, balance);

        } catch (FeignException e) {
            if (e.status() == 404) {
                throw new AccountNotFoundException(req.getAccountNumber());
            } else if (e.status() == HttpStatus.BAD_REQUEST.value()) {
                throw new InsufficientBalanceException(e.getMessage());
            }

            throw new RuntimeException("Internal server error");
        }
    }

    public List<Transaction> getAllTransactions(String accountNumber) {
        return transactionRepo.findAllByAccountNumber(accountNumber);
    }

    public TransactResponseDTO transact(TransactRequestDTO req) {
        DepositRequestDTO debitReq = DepositRequestDTO.builder()
                .accountNumber(req.getFromAccountNumber())
                .transactionType("DEBIT")
                .amount(req.getAmount())
                .reference("transacting from **** **** %s to **** **** %s".formatted(req.getFromAccountNumber().substring(8), req.getToAccountNumber().substring(8)))
                .remarks(req.getRemarks())
                .build();

        DepositRequestDTO creditReq = DepositRequestDTO.builder()
                .accountNumber(req.getToAccountNumber())
                .transactionType("CREDIT")
                .amount(req.getAmount())
                .reference("received from **** **** %s to **** **** %s".formatted(req.getFromAccountNumber().substring(8), req.getToAccountNumber().substring(8)))
                .remarks(req.getRemarks())
                .build();

        DepositResponseDTO debitRes = depositMoney(debitReq);
        depositMoney(creditReq);

        return new TransactResponseDTO(debitRes, req.getFromAccountNumber(), req.getToAccountNumber());
    }

    public List<Transaction> getAllUserTransactions(String userId, String limited) {
        List<Transaction> res;
        List<String> accountNumbers = accountInterface.getAllAccountNumbers(userId);

        List<Transaction> allTransactions = transactionRepo.findAllByUserId(accountNumbers);

        if (limited == null) {
            res = allTransactions.stream()
                    .toList();
        } else {
            res = allTransactions.stream()
                    .limit(6)
                    .toList();
        }


        return res;
    }
}
