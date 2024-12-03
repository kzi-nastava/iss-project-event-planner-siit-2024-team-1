package com.example.eventplanner.model.event;

import com.example.eventplanner.model.merchandise.Merchandise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "eventtype_category",
            joinColumns = @JoinColumn(name = "eventtype_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;
}
