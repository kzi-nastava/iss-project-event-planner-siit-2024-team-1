package com.example.eventplanner.model.merchandise;

import com.example.eventplanner.model.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String comment;
    private int rating;
    private boolean status;

    public Review(String comment, int rating, boolean status) {
        this.comment = comment;
        this.rating = rating;
        this.status = status;
    }

}
