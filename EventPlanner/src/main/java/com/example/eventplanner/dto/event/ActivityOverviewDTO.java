package com.example.eventplanner.dto.event;

import java.time.LocalTime;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ActivityOverviewDTO {
    private int id;
    private String title;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private String address;
}