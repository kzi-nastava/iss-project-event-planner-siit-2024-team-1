package com.example.eventplanner.dto.user;

import com.example.eventplanner.model.user.User;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSuspensionDTO {
    private Long id;
    private User user;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
    private boolean active;
}
