package com.example.eventplanner.services.notification;

import com.example.eventplanner.dto.notification.NotificationDTO;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.user.Notification;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.notification.NotificationRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Transactional
    public void sendNotificationToUser(User user, String content) {
        Notification notification = new Notification();
        notification.setContent(content);
        notification.setRead(false);

        // Save notification first
        notification = notificationRepository.save(notification);

        // Add to user's notifications and save user
        user.getNotifications().add(notification);
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
        return dto;
    }

    @Transactional
    public void notifyUsersEventChanged(int eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<User> affectedUsers = userRepository.findByFollowedEvents_Id(eventId);
        for (User user : affectedUsers) {
            sendNotificationToUser(user, "Event: " + event.getTitle() + " has been updated");
        }
    }

    public NotificationDTO MarkAsRead(int notificationId) {
        Optional<Notification> notification=notificationRepository.findById(notificationId);
        if(notification.isPresent()){
            notification.get().setRead(true);
            notificationRepository.save(notification.get());
            return new NotificationDTO(notification.get().getId(), notification.get().getContent(), notification.get().isRead());
        }
        throw new RuntimeException("Notification not found");
    }

}
