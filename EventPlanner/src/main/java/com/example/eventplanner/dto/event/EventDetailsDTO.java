package com.example.eventplanner.dto.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EventDetailsDTO {
    private int id;
    private String type;
    private String title;
    private Date date;
    private AddressDTO address;
    private String description;
    private int maxParticipants;
    private boolean isPublic;
}
