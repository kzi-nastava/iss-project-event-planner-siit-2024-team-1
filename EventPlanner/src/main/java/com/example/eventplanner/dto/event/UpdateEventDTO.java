package com.example.eventplanner.dto.event;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.example.eventplanner.dto.common.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class UpdateEventDTO {
    private String title;
    private String description;
    private int maxParticipants;
    private boolean isPublic;
    private LocalDateTime date;
    private AddressDTO address; // Embeddable Address representation
    private List<Integer> eventTypeIds; // IDs of selected event types
    private List<Integer> productIds;   // IDs of selected products
    private List<Integer> serviceIds;
}