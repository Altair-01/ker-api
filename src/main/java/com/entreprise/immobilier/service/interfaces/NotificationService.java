package com.entreprise.immobilier.service.interfaces;

import com.entreprise.immobilier.dto.NotificationDTO;
import com.entreprise.immobilier.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    List<Notification> getAllNotifications();

    Optional<Notification> getNotificationById(Long id);

    Notification createNotification(NotificationDTO dto);

    Notification markAsRead(Long id);

    void deleteNotification(Long id);

    List<Notification> getNotificationsByUser(Long userId);

    List<Notification> getUnreadNotifications(Long userId);
}
