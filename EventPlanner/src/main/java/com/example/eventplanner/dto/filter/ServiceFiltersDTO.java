package com.example.eventplanner.dto.filter;

import lombok.Data;

@Data
public class ServiceFiltersDTO {
    private Double priceMin;
    private Double priceMax;
    private String category;
    private Integer durationMin;
    private Integer durationMax;
    private String city;
}
