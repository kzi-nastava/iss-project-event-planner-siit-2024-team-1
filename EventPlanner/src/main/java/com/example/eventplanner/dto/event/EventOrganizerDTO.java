package com.example.eventplanner.dto.event;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventOrganizerDTO {
    private int id;
    private String name;
    private String surname;
    private String email;
}
