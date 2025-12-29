package com.santa.log_service.consumer;

import com.santa.log_service.dto.LogDTO;
import com.santa.log_service.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class LogConsumer {

    private final LogService logService;

    @Autowired
    public LogConsumer(LogService logService) {
        this.logService = logService;
    }

    @KafkaListener(topics = "create-log", groupId = "create-log-group")
    public void createLog(LogDTO log){
        logService.addLog(log);
    }
}