package com.example.eventplanner.dto.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.model.common.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
public class  EventDetailsDTO {
    private int id;
    private String title;
    private LocalDateTime date;
    private AddressDTO address;
    private String description;
    private int maxParticipants;
    private EventTypeOverviewDTO eventType;
    @JsonProperty("isPublic")
    private boolean isPublic;
    private EventOrganizerDTO organizer;
}
