package com.santa.log_service.dto;

import com.santa.log_service.model.LogLevel;
import com.santa.log_service.model.LogServiceType;
import lombok.Data;

@Data
public class LogDTO {
    private LogServiceType serviceType;
    private LogLevel logLevel;
    private String message;
}
