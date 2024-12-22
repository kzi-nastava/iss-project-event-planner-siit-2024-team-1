package com.example.eventplanner.dto.merchandise.service;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CalendarTimeSlotDTO {
    private String service;
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
