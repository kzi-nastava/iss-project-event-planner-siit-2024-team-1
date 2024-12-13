package com.example.eventplanner.dto.user;

import com.example.eventplanner.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSuspensionDTO {
    private Long id;
    private int userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String reason;
}
