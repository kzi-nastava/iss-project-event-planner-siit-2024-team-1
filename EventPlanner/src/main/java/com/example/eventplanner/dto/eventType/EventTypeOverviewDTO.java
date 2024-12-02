package com.example.eventplanner.dto.eventType;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EventTypeOverviewDTO {
    private int id;
    private String title;
    private String description;
    private boolean active;
    private List<CategoryOverviewDTO> recommendedCategories;
}
