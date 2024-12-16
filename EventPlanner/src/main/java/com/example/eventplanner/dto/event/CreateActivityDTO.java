package com.example.eventplanner.dto.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.model.event.Activity;

import java.time.LocalTime;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class CreateActivityDTO {
    private String title;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private AddressDTO address;
}
