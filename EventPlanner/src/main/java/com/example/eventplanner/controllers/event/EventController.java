package com.example.eventplanner.controllers.event;

import com.example.eventplanner.dto.event.CreateEventDTO;
import com.example.eventplanner.dto.event.CreatedEventOverviewDTO;
import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.eventType.CreateEventTypeDTO;
import com.example.eventplanner.dto.filter.EventFiltersDTO;
import com.example.eventplanner.services.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    @GetMapping("/top")
    public ResponseEntity<Page<EventOverviewDTO>> getTopEvents(
            @PageableDefault(size = 5, sort = "date", direction = Sort.Direction.DESC) Pageable eventPage
    ) {
        return ResponseEntity.ok(eventService.getTop(eventPage));
    }
    @GetMapping("/all")
    public ResponseEntity<Page<EventOverviewDTO>> getAllEvents(
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable eventPage
    ) {
        return ResponseEntity.ok(eventService.getAll(eventPage));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EventOverviewDTO>> filterEvents(
            @RequestBody EventFiltersDTO eventFilters,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {

        List<EventOverviewDTO> emptyDTOs = Collections.nCopies(pageable.getPageSize(), new EventOverviewDTO());
        Page<EventOverviewDTO> eventDTOs = new PageImpl<>(emptyDTOs, pageable, 0);

        return ResponseEntity.ok(eventDTOs);
    }

    @PostMapping
    public ResponseEntity<CreatedEventOverviewDTO> createEvent(@RequestBody CreateEventDTO dto) {
        // Call the service to create the event
        CreatedEventOverviewDTO createdEvent = eventService.createEvent(dto);

        // Return the created event DTO
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }
}
