package com.example.eventplanner.model.user;

import com.example.eventplanner.model.event.Event;
import jakarta.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;
    private boolean read;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User intendedTo;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public User getIntendedTo() {
        return intendedTo;
    }

    public void setIntendedTo(User intendedTo) {
        this.intendedTo = intendedTo;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
