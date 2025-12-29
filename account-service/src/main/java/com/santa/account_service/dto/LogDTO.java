package com.santa.account_service.dto;

import com.santa.account_service.model.LogLevel;
import com.santa.account_service.model.LogServiceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogDTO {
    private LogServiceType serviceType;
    private LogLevel logLevel;
    private String message;
}
