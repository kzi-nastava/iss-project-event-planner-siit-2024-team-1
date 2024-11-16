package com.example.eventplanner.model.event;

import com.example.eventplanner.model.merchandise.Merchandise;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "eventtype_merchandise",
            joinColumns = @JoinColumn(name = "eventtype_id"),
            inverseJoinColumns = @JoinColumn(name = "merchandise_id")
    )
    private List<Merchandise> merchandise;

    @OneToMany(mappedBy = "eventType")
    private List<Category> categories;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
