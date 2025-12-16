package com.santa.auth_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("ACCOUNT-SERVICE")
public interface AccountInterface {
    @GetMapping("api/accounts/total-accounts")
    public int getTotalAccount(@RequestHeader("userId") String userId);
}
