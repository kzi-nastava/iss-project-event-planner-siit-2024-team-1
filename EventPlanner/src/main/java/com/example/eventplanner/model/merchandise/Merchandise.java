package com.example.eventplanner.model.merchandise;

import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.common.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // All subclasses stored in one table
@DiscriminatorColumn(name = "merchandise_type", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Merchandise {
    @Id
    @TableGenerator(
            name = "merchandise",
            table = "id_generator",
            pkColumnName = "sequence_name",
            valueColumnName = "next_val"
    )
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "merchandise")
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
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "photo_id"))
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

    @ManyToMany  // Many-to-many with EventType
    @JoinTable(
            name = "merchandise_eventtype",
            joinColumns = @JoinColumn(name = "merchandise_id"),
            inverseJoinColumns = @JoinColumn(name = "eventtype_id")
    )
    private List<EventType> eventTypes;

    @Transient
    private double rating;


    public double getRating() {
        if (this.reviews != null) {
            return this.reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
        }
        return 0.0;
    }

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
