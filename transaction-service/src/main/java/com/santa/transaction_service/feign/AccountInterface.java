package com.santa.transaction_service.feign;

import com.santa.transaction_service.dto.TransactionRequestDTO;
import com.santa.transaction_service.dto.TransactionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ACCOUNT-SERVICE")
public interface AccountInterface {
    @PutMapping("api/accounts/transact")
    ResponseEntity<TransactionResponseDTO> updateAccountBalance(@RequestBody TransactionRequestDTO req);

    @GetMapping("api/accounts/all-account-numbers")
    public List<String> getAllAccountNumbers(@RequestHeader("userId") String userId);
}