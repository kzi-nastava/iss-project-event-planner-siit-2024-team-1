package com.example.eventplanner.dto.merchandise.service;

import lombok.Data;

@Data
public class FilterServiceRequestDTO {
    private String category;
    private String eventType;
    private double maxPrice;
    private boolean available;
}
