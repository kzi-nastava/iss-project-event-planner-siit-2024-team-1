package com.example.eventplanner.controllers.dashboard;

import com.example.eventplanner.dto.dashboard.EventAndMerchandiseOverviewDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {
    @GetMapping("/home")
    public ResponseEntity<EventAndMerchandiseOverviewDTO> getTopEventsAndMerchandise() {
        return ResponseEntity.ok(new EventAndMerchandiseOverviewDTO());
    }
    @GetMapping("/home/all")
    public ResponseEntity<EventAndMerchandiseOverviewDTO> getAllEventsAndMerchandise() {
        return ResponseEntity.ok(new EventAndMerchandiseOverviewDTO());
    }
}
