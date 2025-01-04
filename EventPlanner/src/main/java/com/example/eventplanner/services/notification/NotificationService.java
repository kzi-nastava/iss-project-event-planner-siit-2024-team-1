package com.example.eventplanner.services.notification;

import com.example.eventplanner.dto.merchandise.review.ReviewOverviewDTO;
import com.example.eventplanner.dto.notification.NotificationDTO;
import com.example.eventplanner.model.common.NotificationType;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.common.Notification;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.notification.NotificationRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void sendNotificationToUser(User user, String content, NotificationType type,int entityId) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setRead(false);
        notification.setDate(LocalDateTime.now());
        notification.setType(type);
        notification.setEntityId(entityId);

        // Save notification first
        notification = notificationRepository.save(notification);

        // Add to user's notifications and save user
        user.getNotifications().add(notification);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(user.getId()),
                "/notifications",  // Will become /user/notifications
                mapToDTO(notification)
        );
        userRepository.save(user);
    }

    public List<NotificationDTO> getUnreadNotifications(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getNotifications().stream()
                    .filter(notification -> !notification.isRead())
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<NotificationDTO> getReadNotifications(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getNotifications().stream()
                    .filter(notification -> notification.isRead())
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private NotificationDTO mapToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setContent(notification.getContent());
        dto.setRead(notification.isRead());
        dto.setDate(notification.getDate());
        dto.setType(notification.getType());
        dto.setEntityId(notification.getEntityId());
        return dto;
    }

    @Transactional
    public void notifyUsersEventChanged(int eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<User> affectedUsers = userRepository.findByFollowedEvents_Id(eventId);
        for (User user : affectedUsers) {
            sendNotificationToUser(user, "Event: " + event.getTitle() + " has been updated", NotificationType.EVENT,eventId);
        }
    }

    @Transactional
    public void notifyOfNewReview(int userId, ReviewOverviewDTO review,int reviewedEntityId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())throw new RuntimeException("User not found");
        String type=review.getReviewedType();
        NotificationType notificationType=NotificationType.valueOf(type);
        sendNotificationToUser(user.get(), "User: " + review.getReviewerUsername() + "\n has reviewed: "+
                review.getReviewedTitle()+"\n"+
                "Rating: "+review.getRating()+"/5\n"+
                "Comment: "+review.getComment(),notificationType,reviewedEntityId);
    }

    public NotificationDTO MarkAsRead(int notificationId) {
        Optional<Notification> notification=notificationRepository.findById(notificationId);
        if(notification.isPresent()){
            notification.get().setRead(true);
            notificationRepository.save(notification.get());
            return mapToDTO(notification.get());
        }
        throw new RuntimeException("Notification not found");
    }

}
