package com.santa.transaction_service.feign;

import com.santa.transaction_service.dto.TransactionRequestDTO;
import com.santa.transaction_service.dto.TransactionResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("ACCOUNT-SERVICE")
public interface AccountInterface {
    @PutMapping("accounts/{accountId}/transact")
    public ResponseEntity<TransactionResponseDTO> updateAccountBalance(
            @PathVariable String accountId,
            @RequestBody TransactionRequestDTO req);
}