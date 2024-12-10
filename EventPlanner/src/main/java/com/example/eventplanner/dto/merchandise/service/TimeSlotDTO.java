package com.example.eventplanner.dto.merchandise.service;

import com.example.eventplanner.model.merchandise.Timeslot;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeSlotDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Constructor
    public TimeSlotDTO(Timeslot timeslot) {
        this.id = timeslot.getId();
        this.startTime = timeslot.getStartTime();
        this.endTime = timeslot.getEndTime();
    }

    // Getters and setters
    // ...
}
