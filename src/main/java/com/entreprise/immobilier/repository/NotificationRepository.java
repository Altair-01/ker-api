package com.entreprise.immobilier.repository;

import com.entreprise.immobilier.model.Notification;
import com.entreprise.immobilier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
    List<Notification> findByUserAndIsReadFalse(User user);
}
