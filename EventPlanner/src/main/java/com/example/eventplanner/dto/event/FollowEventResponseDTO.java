package com.example.eventplanner.dto.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowEventResponseDTO {
    public int eventId;
    public int userId;
}
