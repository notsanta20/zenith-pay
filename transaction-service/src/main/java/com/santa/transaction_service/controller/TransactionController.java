package com.santa.transaction_service.controller;

import com.santa.transaction_service.dto.*;
import com.santa.transaction_service.model.LogLevel;
import com.santa.transaction_service.model.LogServiceType;
import com.santa.transaction_service.model.Transaction;
import com.santa.transaction_service.producer.LogProducer;
import com.santa.transaction_service.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final LogProducer logProducer;

    @Autowired
    public TransactionController(TransactionService transactionService, LogProducer logProducer) {
        this.transactionService = transactionService;
        this.logProducer = logProducer;
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponseDTO> depositMoney(@RequestBody DepositRequestDTO req){
        DepositResponseDTO res = transactionService.depositMoney(req);

                LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.INFO)
                .serviceType(LogServiceType.TRANSACTION)
                .message("transaction made on account number - **** **** %s.".formatted(req.getAccountNumber().substring(8)))
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/all-transactions/{accountNumber}")
    public ResponseEntity<List<Transaction>> getAllTransactions(@PathVariable String accountNumber){
        List<Transaction> res = transactionService.getAllTransactions(accountNumber);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/transact")
    public ResponseEntity<TransactResponseDTO> transact(@RequestBody TransactRequestDTO req){
        TransactResponseDTO res = transactionService.transact(req);

                LogDTO log = LogDTO.builder()
                .logLevel(LogLevel.INFO)
                .serviceType(LogServiceType.TRANSACTION)
                .message("transacted from **** **** %s to **** **** %s.".formatted(req.getFromAccountNumber().substring(8),req.getToAccountNumber().substring(8)))
                .build();

        logProducer.createLog(log);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/all-transactions")
    public ResponseEntity<List<Transaction>> getAllUserTransactions(@RequestHeader("userId") String userId, @RequestParam(required = false) String limited){
        List<Transaction> res = transactionService.getAllUserTransactions(userId, limited);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
