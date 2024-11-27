package com.example.eventplanner.controllers.event;

import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.filter.EventFiltersDTO;
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
@RequestMapping("/api/v1/events")
public class EventController {
    @GetMapping("/top")
    public ResponseEntity<Page<EventOverviewDTO>> getTopEvents(
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable eventPage
    ) {
        // Get paginated events from service
        List<EventOverviewDTO> emptyDTOs = Collections.nCopies(eventPage.getPageSize(), new EventOverviewDTO());
        Page<EventOverviewDTO> eventDTOs = new PageImpl<>(emptyDTOs, eventPage, 0);

        return ResponseEntity.ok(eventDTOs);
    }
    @GetMapping("/all")
    public ResponseEntity<Page<EventOverviewDTO>> getAllEvents(
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable eventPage
    ) {
        // Get paginated events from service
        List<EventOverviewDTO> emptyDTOs = Collections.nCopies(eventPage.getPageSize(), new EventOverviewDTO());
        Page<EventOverviewDTO> eventDTOs = new PageImpl<>(emptyDTOs, eventPage, 0);

        return ResponseEntity.ok(eventDTOs);
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
}
