package com.example.eventplanner.dto.filter;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventFiltersDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String city;
}
