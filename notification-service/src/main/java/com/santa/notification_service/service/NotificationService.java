package com.santa.notification_service.service;

import com.santa.notification_service.dto.NotificationRequestDTO;
import com.santa.notification_service.dto.NotificationResponseDTO;
import com.santa.notification_service.model.Notification;
import com.santa.notification_service.model.NotificationType;
import com.santa.notification_service.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepo notificationRepo;

    @Autowired
    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
    }

    public List<NotificationResponseDTO> getAllNotifications(String userId) {
        List<Notification> allNotifications = notificationRepo.findAllByUserId(userId);

        return allNotifications.stream()
                .filter(n->!n.isRead())
                .map(NotificationResponseDTO::new)
                .toList();
    }

    public boolean addNotification(NotificationRequestDTO req) {
        try{
            Notification notification = Notification.builder()
                    .userId(req.getUserId())
                    .notificationType(NotificationType.valueOf(req.getNotificationType()))
                    .message(req.getMessage())
                    .isRead(false)
                    .createdAt(LocalDateTime.now())
                    .readAt(null)
                    .build();

            notificationRepo.save(notification);

            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public String markAsRead(String userId) {
        try{
            List<Notification> allNotifications = notificationRepo.findAllByUserId(userId);

            allNotifications.stream()
                    .filter(n->!n.isRead())
                    .forEach(n->{
                        n.setRead(true);
                        n.setReadAt(LocalDateTime.now());
                        notificationRepo.save(n);
                    });

            return "Successfully updated notification status";
        } catch (Exception e) {
            return "Failed to update notification status";
        }
    }
}