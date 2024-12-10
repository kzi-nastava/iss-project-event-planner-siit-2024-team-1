package com.example.eventplanner.controllers.event;

import com.example.eventplanner.dto.event.*;
import com.example.eventplanner.dto.filter.EventFiltersDTO;
import com.example.eventplanner.services.JwtService;
import com.example.eventplanner.services.event.EventService;
import com.example.eventplanner.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final UserService userService;
    private final JwtService jwtService;
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

    @GetMapping("/eo/{id}")
    public ResponseEntity<Page<EventOverviewDTO>> getEventsByEo(@PathVariable int id,
            @PageableDefault(size = 10, sort = "date", direction = Sort.Direction.DESC) Pageable eventPage
    ) {
        return ResponseEntity.ok(eventService.getByEo(id, eventPage));
    }

    @GetMapping("/followed")
    public ResponseEntity<List<EventOverviewDTO>> getFollowedEvents(
            @RequestParam int userId
    ) {
        return ResponseEntity.ok(eventService.getUserFollowedEvents(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<EventOverviewDTO>> filterEvents(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {
        EventFiltersDTO eventFiltersDTO=new EventFiltersDTO(startDate,endDate,type,city);
        return ResponseEntity.ok(eventService.search(eventFiltersDTO,search,pageable));
    }

    @GetMapping("/{id}/agenda")
    public ResponseEntity<List<ActivityOverviewDTO>> getAgenda(@PathVariable int id) {
        return ResponseEntity.ok(eventService.getAgenda(id));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<EventDetailsDTO> getDetails(@PathVariable int id) {
        return ResponseEntity.ok(eventService.getDetails(id));
    }

    @PostMapping
    public ResponseEntity<CreatedEventOverviewDTO> createEvent(@RequestBody CreateEventDTO dto) {
        // Call the service to create the event
        CreatedEventOverviewDTO createdEvent = eventService.createEvent(dto);

        // Return the created event DTO
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreatedEventOverviewDTO> updateEvent(@PathVariable int id, @RequestBody UpdateEventDTO dto) {
        // Call the service to create the event
        CreatedEventOverviewDTO updatedEvent = eventService.updateEvent(id, dto);

        // Return the created event DTO
        return ResponseEntity.ok(updatedEvent);
    }

    @PutMapping("/{id}/agenda")
    public ResponseEntity<CreatedActivityDTO> updateAgenda(@PathVariable int id, @RequestBody CreateActivityDTO dto) {
        // Call the service to create the event
        CreatedActivityDTO activityDTO = eventService.updateAgenda(id, dto);

        // Return the created event DTO
        return new ResponseEntity<>(activityDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("{eventId}/agenda/{activityId}")
    public ResponseEntity<List<ActivityOverviewDTO>> deleteAgenda(@PathVariable int eventId, @PathVariable int activityId) {
        // Call the service to create the event
        List<ActivityOverviewDTO> activityDTOs = eventService.deleteActivity(eventId, activityId);

        // Return the created event DTO
        return ResponseEntity.ok(activityDTOs);
    }

    @PostMapping("/invite")
    public ResponseEntity<InviteResponseDTO> inviteToEvent(
            @RequestParam String email,
            @RequestParam int eventId
    ) {
        return ResponseEntity.ok(jwtService.inviteToEvent(eventId,email));
    }
}
