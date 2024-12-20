package com.example.eventplanner.controllers.notification;

import com.example.eventplanner.dto.notification.NotificationDTO;
import com.example.eventplanner.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@CrossOrigin
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(@PathVariable int userId) {
        try {
            List<NotificationDTO> notifications = notificationService.getUnreadNotifications(userId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/read/{userId}")
    public ResponseEntity<List<NotificationDTO>> getReadNotifications(@PathVariable int userId) {
        try {
            List<NotificationDTO> notifications = notificationService.getReadNotifications(userId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{notificationId}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable int notificationId) {
        try {
            NotificationDTO notification = notificationService.MarkAsRead(notificationId);
            return ResponseEntity.ok(notification);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}