package com.santa.transaction_service.controller;

import com.santa.transaction_service.dto.DepositRequestDTO;
import com.santa.transaction_service.dto.DepositResponseDTO;
import com.santa.transaction_service.dto.TransactRequestDTO;
import com.santa.transaction_service.dto.TransactResponseDTO;
import com.santa.transaction_service.model.Transaction;
import com.santa.transaction_service.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositResponseDTO> depositMoney(@RequestBody DepositRequestDTO req){
        DepositResponseDTO res = transactionService.depositMoney(req);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/all-transactions/{accountNumber}")
    public ResponseEntity<Page<Transaction>> getAllTransactions(
            @PathVariable String accountNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        Page<Transaction> res = transactionService.getAllTransactions(accountNumber, page, size);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/transact")
    public ResponseEntity<TransactResponseDTO> transact(@RequestBody TransactRequestDTO req){
        TransactResponseDTO res = transactionService.transact(req);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
