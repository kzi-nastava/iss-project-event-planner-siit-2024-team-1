package com.example.eventplanner.dto.event;

import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class EventOverviewDTO {
    private int id;
    private String type;
    private String title;
    private LocalDateTime date;
    private Address address;
    private String description;
}
