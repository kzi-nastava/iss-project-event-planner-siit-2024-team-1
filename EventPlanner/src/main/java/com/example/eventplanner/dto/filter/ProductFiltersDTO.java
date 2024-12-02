package com.example.eventplanner.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductFiltersDTO {
    private Double priceMin;
    private Double priceMax;
    private String category;
    private Integer durationMin;
    private Integer durationMax;
    private String city;
}
