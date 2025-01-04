package com.example.eventplanner.model.event;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.common.Review;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.model.common.Notification;
import com.example.eventplanner.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private int maxParticipants;
    private boolean isPublic;
    private LocalDateTime date;
    private double maxBudget;


    @Embedded
    private Address address;

    @OneToMany
    @JoinTable(
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> activities;

    @ManyToOne
    private EventType type;

    @OneToMany
    @JoinTable(
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name = "events_merchandise",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "merchandise_id")
    )
    private List<Merchandise> merchandise;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "budget_id", referencedColumnName = "budgetId")
    private Budget budget;

    @ManyToMany
    private List<User> participants;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private EventOrganizer organizer;
}