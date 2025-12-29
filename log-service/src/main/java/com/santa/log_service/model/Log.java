package com.santa.log_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "logs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    private String id;
    private LogServiceType serviceType;
    private LogLevel logLevel;
    private String message;
    private LocalDateTime timestamp;
}
