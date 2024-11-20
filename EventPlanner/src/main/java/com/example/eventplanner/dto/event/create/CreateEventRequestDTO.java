package com.example.eventplanner.dto.event.create;

import com.example.eventplanner.model.event.Activity;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.user.EventOrganizer;

import java.util.Date;
import java.util.List;

public class CreateEventRequestDTO {
    private String title;
    private String description;
    private int maxParticipants;
    private boolean isPublic;
    private Date eventDate;
    private double maxBudget;

    private Address address;

    private EventOrganizer organizer;

    private List<Activity> activities;

    private EventType type;

    private List<Merchandise> merchandise;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(double maxBudget) {
        this.maxBudget = maxBudget;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public EventOrganizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(EventOrganizer organizer) {
        this.organizer = organizer;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public List<Merchandise> getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(List<Merchandise> merchandise) {
        this.merchandise = merchandise;
    }
}
