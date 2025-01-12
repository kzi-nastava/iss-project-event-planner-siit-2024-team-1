package com.example.eventplanner.model.merchandise;

import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "time_slots")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Timeslot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
    @ManyToOne
    private Event event;

    public Timeslot(LocalDateTime startTime, LocalDateTime endTime,Event event) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.event = event;
    }
}
