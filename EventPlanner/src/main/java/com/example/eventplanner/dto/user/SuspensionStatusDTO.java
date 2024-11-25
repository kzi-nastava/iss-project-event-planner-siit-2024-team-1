package com.example.eventplanner.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class SuspensionStatusDTO {
    private boolean suspended;
    private LocalDateTime endTime;
    private Duration remainingTime;
}
