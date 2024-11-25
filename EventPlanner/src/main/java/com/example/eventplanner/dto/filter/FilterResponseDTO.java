package com.example.eventplanner.dto.filter;

import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import lombok.Data;

import java.util.List;

@Data
public class FilterResponseDTO {
    private List<EventOverviewDTO> events;
    private List<MerchandiseOverviewDTO> services;
    private List<MerchandiseOverviewDTO> products;
    private long totalResults;
}
