package com.santa.auth_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient("USER-SERVICE")
public interface ProfileInterface {
    @GetMapping("api/profile/kyc-status")
    public boolean checkKycStatus(@RequestHeader("userId") String userId);

}

