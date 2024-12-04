package com.example.eventplanner.dto.category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryOverviewDTO {
    private int id;
    private String title;
    private String description;
    private boolean pending;
}
