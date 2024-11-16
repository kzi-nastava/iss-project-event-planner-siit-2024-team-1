package com.example.eventplanner.dto.eventType;

import com.example.eventplanner.model.event.EventType;

import java.util.List;

public class GetAllEventTypesResponseDTO {
    private List<EventType> eventTypes;

    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }
}
