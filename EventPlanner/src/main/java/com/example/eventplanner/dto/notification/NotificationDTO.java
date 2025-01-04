package com.example.eventplanner.dto.notification;

import com.example.eventplanner.model.common.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private int id;

    private String content;
    private boolean read;
    private LocalDateTime date;
    private NotificationType type;
    private int entityId;
}
