package com.example.eventplanner.dto.eventType.create;

import com.example.eventplanner.model.event.Category;

import java.util.List;

public class CreateEventTypeResponseDTO {
    private String title;
    private String description;
    private List<Category> categories;
    private boolean active;

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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
