package com.example.eventplanner.dto.event;

import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EventOverviewDTO {
    private String type;
    private String title;
    private Date date;
    private Address address;
    private String description;
}
