package com.santa.user_service.dto;

import com.santa.user_service.model.LogLevel;
import com.santa.user_service.model.LogServiceType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogDTO {
    private LogServiceType serviceType;
    private LogLevel logLevel;
    private String message;
}
