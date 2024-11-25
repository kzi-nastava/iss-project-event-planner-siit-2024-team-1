package com.example.eventplanner.dto.dashboard;

import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EventAndMerchandiseOverviewDTO {
    private List<EventOverviewDTO> topEvents;
    private List<MerchandiseOverviewDTO> topServices;
}
