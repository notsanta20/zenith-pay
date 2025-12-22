package com.santa.auth_service.feign;

import com.santa.auth_service.dto.UserStatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient("USER-SERVICE")
public interface ProfileInterface {
    @GetMapping("api/profile/user-status")
    UserStatusDTO getUserStatus(@RequestHeader("userId") String userId);

    @GetMapping("api/profile/username")
    String getUsername(@RequestHeader("userId") String userId);

}

