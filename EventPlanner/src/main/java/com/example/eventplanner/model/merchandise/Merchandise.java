package com.example.eventplanner.model.merchandise;

import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.common.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // All subclasses stored in one table
@DiscriminatorColumn(name = "merchandise_type", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchandise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String specificity;
    private double price;
    private int discount;
    private boolean visible;
    private boolean available;
    private int minDuration;
    private int maxDuration;
    private int reservationDeadline;
    private int cancellationDeadline;
    private boolean automaticReservation;
    private boolean deleted;


    @OneToMany
    private List<MerchandisePhoto> photos;

    @OneToMany
    @JoinTable(
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private List<Review> reviews;

    @ManyToMany(mappedBy = "merchandise")
    private List<Event> events;


    @Embedded
    private Address address;

    @ManyToOne
    private Category category;


    public Merchandise(String description, String title, String specificity, double price, int discount, boolean visible, boolean available, int minDuration, int maxDuration, int reservationDeadline, int cancellationDeadline, boolean automaticReservation, boolean deleted, List<MerchandisePhoto> photos, List<Review> reviews, List<Event> events, Address address, Category category) {
        this.description = description;
        this.title = title;
        this.specificity = specificity;
        this.price = price;
        this.discount = discount;
        this.visible = visible;
        this.available = available;
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
        this.reservationDeadline = reservationDeadline;
        this.cancellationDeadline = cancellationDeadline;
        this.automaticReservation = automaticReservation;
        this.deleted = deleted;
        this.photos = photos;
        this.reviews = reviews;
        this.events = events;
        this.address = address;
        this.category = category;
    }
}
