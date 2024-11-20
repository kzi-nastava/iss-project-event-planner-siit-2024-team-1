package com.example.eventplanner.model.user;

import com.example.eventplanner.model.event.Event;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@DiscriminatorValue("EventOrganizer")
public class EventOrganizer extends User {
    @OneToMany
    private List<Event> organizingEvents;

    // Getters and Setters
    public List<Event> getOrganizingEvents() {
        return organizingEvents;
    }

    public void setOrganizingEvents(List<Event> organizingEvents) {
        this.organizingEvents = organizingEvents;
    }
}