package com.example.eventplanner.controllers.eventType;

import com.example.eventplanner.dto.eventType.CreateEventTypeDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.eventType.UpdateEventTypeDTO;
import com.example.eventplanner.dto.filter.EventFiltersDTO;
import com.example.eventplanner.services.eventType.EventTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/event-types")
@RequiredArgsConstructor
public class EventTypeController {
    private final EventTypeService eventTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<EventTypeOverviewDTO>> getAllEventTypes(
            @PageableDefault(size = 10) Pageable eventPage
    ) {
        return ResponseEntity.ok(eventTypeService.getAll());
    }

    @PostMapping
    public ResponseEntity<EventTypeOverviewDTO> createEventType(@RequestBody CreateEventTypeDTO dto) {
        EventTypeOverviewDTO createdEventType = eventTypeService.create(dto);
        return ResponseEntity.status(201).body(createdEventType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventTypeOverviewDTO> updateEventType(
            @PathVariable int id,
            @RequestBody UpdateEventTypeDTO dto) {
        EventTypeOverviewDTO updatedEventType = eventTypeService.update(id, dto);
        return ResponseEntity.ok(updatedEventType);
    }
}
