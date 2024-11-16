package com.example.eventplanner.model.event;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.Review;
import com.example.eventplanner.model.user.Address;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.model.user.Notification;
import com.example.eventplanner.model.user.User;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private int maxParticipants;
    private boolean isPublic;
    private Date eventDate;
    private double maxBudget;

    @OneToMany(mappedBy = "event")
    private List<Notification> notifications;

    @ManyToOne
    private Address address;

    @ManyToOne
    private EventOrganizer organizer;

    @OneToMany(mappedBy = "event")
    private List<Activity> activities;

    @ManyToOne
    private EventType type;

    @OneToMany(mappedBy = "event")
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "event_merchandise",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "merchandise_id")
    )
    private List<Merchandise> merchandise;

    @ManyToMany
    @JoinTable(name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
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

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Merchandise> getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(List<Merchandise> merchandise) {
        this.merchandise = merchandise;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}