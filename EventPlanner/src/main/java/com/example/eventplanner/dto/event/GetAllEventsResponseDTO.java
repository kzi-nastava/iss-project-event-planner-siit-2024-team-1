package com.example.eventplanner.dto.event;

import com.example.eventplanner.model.event.Event;

import java.util.List;

public class GetAllEventsResponseDTO {
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
