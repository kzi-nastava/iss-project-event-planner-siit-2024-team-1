package com.example.eventplanner.dto.category;

import lombok.Data;

@Data
public class CategoryRequestDTO {
    private String title;
    private String description;
    private boolean pending;
}
