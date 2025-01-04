package com.example.eventplanner.repositories.notification;

import com.example.eventplanner.model.common.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
