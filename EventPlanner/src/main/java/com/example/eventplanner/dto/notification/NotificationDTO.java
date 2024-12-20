package com.example.eventplanner.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private int id;

    private String content;
    private boolean read;
}
