package com.santa.notification_service.controller;

import com.santa.notification_service.dto.NotificationRequestDTO;
import com.santa.notification_service.dto.NotificationResponseDTO;
import com.santa.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications(@RequestHeader("userId") String userId) {
        List<NotificationResponseDTO> res = notificationService.getAllNotifications(userId);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/add")
    public boolean addNotification(@RequestHeader("userId") String userId, @RequestBody NotificationRequestDTO req){
        return notificationService.addNotification(userId,req);
    }

    @PutMapping("/read")
    public String markAsRead(@RequestHeader("userId") String userId){
        return notificationService.markAsRead(userId);
    }
}
