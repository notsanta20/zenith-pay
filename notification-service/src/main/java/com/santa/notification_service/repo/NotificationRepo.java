package com.santa.notification_service.repo;

import com.santa.notification_service.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends MongoRepository<Notification, String> {
    List<Notification> findAllByUserId(String userId);
}
