package com.example.eventplanner.dto.merchandise.product;

import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateCategoryDTO {
    private String title;
    private String description;
    private boolean pending;
    private List<Integer> eventTypeIds;
}
