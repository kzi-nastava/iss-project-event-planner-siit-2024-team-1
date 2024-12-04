package com.example.eventplanner.dto.event;

import java.util.Date;
import java.util.List;

import com.example.eventplanner.dto.common.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class CreateEventDTO {
    private String title;
    private String description;
    private int maxParticipants;
    private boolean isPublic;
    private Date date;
    private AddressDTO address; // Embeddable Address representation
    private int eventTypeId; // IDs of selected event types
    private List<Integer> productIds;   // IDs of selected products
    private List<Integer> serviceIds;
}
