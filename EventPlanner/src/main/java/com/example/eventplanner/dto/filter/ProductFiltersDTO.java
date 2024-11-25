package com.example.eventplanner.dto.filter;

import lombok.Data;

@Data
class ProductFiltersDTO {
    private Double priceMin;
    private Double priceMax;
    private String category;
    private Integer durationMin;
    private Integer durationMax;
    private String city;
}
