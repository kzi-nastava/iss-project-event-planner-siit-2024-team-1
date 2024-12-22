package com.example.eventplanner.dto.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetServiceByIdResponseDTO;
import com.example.eventplanner.model.user.EventOrganizer;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class CreatedEventOverviewDTO {
    private int id;
    private String title;
    private String description;
    private int maxParticipants;
    @JsonProperty("isPublic")
    private boolean isPublic;
    private LocalDateTime date;
    private AddressDTO address; // Embeddable Address representation
    private EventTypeOverviewDTO eventType; // IDs of selected event types
    private List<GetProductByIdResponseDTO> products;   // IDs of selected products
    private List<GetServiceByIdResponseDTO> services;
    private EventOrganizerDTO organizer;
}
