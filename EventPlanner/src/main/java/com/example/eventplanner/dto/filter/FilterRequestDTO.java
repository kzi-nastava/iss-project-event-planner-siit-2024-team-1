package com.example.eventplanner.dto.filter;

import lombok.Data;

@Data
public class FilterRequestDTO {
    private FilterOptionsDTO filterOptionsDTO;
    private EventFiltersDTO events;
    private ServiceFiltersDTO services;
    private ProductFiltersDTO products;
}
