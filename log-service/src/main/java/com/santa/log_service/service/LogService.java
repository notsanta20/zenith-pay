package com.santa.log_service.service;

import com.santa.log_service.dto.LogDTO;
import com.santa.log_service.model.Log;
import com.santa.log_service.model.LogLevel;
import com.santa.log_service.repo.LogRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    private final LogRepo logRepo;
    private final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    public LogService(LogRepo logRepo) {
        this.logRepo = logRepo;
    }

    public void addLog(LogDTO req) {
        Log log = Log.builder()
                .serviceType(req.getServiceType())
                .logLevel(req.getLogLevel())
                .message(req.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        logRepo.save(log);

        switch (req.getLogLevel()) {
            case LogLevel.TRACE -> logger.trace(req.getMessage());
            case LogLevel.DEBUG -> logger.debug(req.getMessage());
            case LogLevel.WARN -> logger.warn(req.getMessage());
            case LogLevel.ERROR -> logger.error(req.getMessage());
            default -> logger.info(req.getMessage());
        }
    }
}
