package com.example.eventplanner.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.*;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.eventType.EventTypeRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("jpatest")
class EventControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private EventOrganizer organizer;
    private Event event;
    private EventType eventType;
    private CreateEventDTO validCreateEventDTO;
    private UpdateEventDTO validUpdateEventDTO;
    @Autowired
    private EventTypeRepository eventTypeRepository;

    @BeforeEach
    void setUp() {
        // Create and save organizer
        organizer = new EventOrganizer();
        organizer.setName("Organizer");
        organizer.setUsername("organizer@test.com");
        organizer.setAddress(new Address());
        organizer = userRepository.save(organizer);

        eventType = new EventType();
        eventType.setTitle("Event Type");
        eventType.setDescription("Event Description");
        eventType.setActive(true);
        eventType.setCategories(new ArrayList<>());
        eventType = eventTypeRepository.save(eventType);

        // Create and save event
        event = new Event();
        event.setDate(LocalDateTime.now().plusDays(7));
        event.setTitle("Test Event");
        event.setOrganizer(organizer);
        event.setType(eventType);
        event.setAddress(new Address());
        event.setMerchandise(new ArrayList<>());
        event = eventRepository.save(event);

        // Prepare valid event DTOs for create and update operations
        validCreateEventDTO = new CreateEventDTO();
        validCreateEventDTO.setTitle("New Event");
        validCreateEventDTO.setAddress(new AddressDTO());
        validCreateEventDTO.setDate(LocalDateTime.now().plusDays(14));
        validCreateEventDTO.setEventTypeId(eventType.getId());
        validCreateEventDTO.setOrganizerId(organizer.getId());

        validUpdateEventDTO = new UpdateEventDTO();
        validUpdateEventDTO.setTitle("Updated Event");
        validUpdateEventDTO.setAddress(new AddressDTO());
        validUpdateEventDTO.setDate(LocalDateTime.now().plusDays(15));
        validUpdateEventDTO.setEventTypeId(eventType.getId());
        validUpdateEventDTO.setServiceIds(new ArrayList<>());
        validUpdateEventDTO.setProductIds(new ArrayList<>());
    }

    @Test
    void createEvent_ValidRequest_ReturnsCreated() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(validCreateEventDTO, headers);

        ResponseEntity<CreatedEventOverviewDTO> response = restTemplate.postForEntity(
                "/api/v1/events",
                request,
                CreatedEventOverviewDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo(validCreateEventDTO.getTitle());
    }

    @Test
    void updateEvent_ValidRequest_ReturnsOk() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateEventDTO> request = new HttpEntity<>(validUpdateEventDTO, headers);


        ResponseEntity<CreatedEventOverviewDTO> response = restTemplate.exchange(
                "/api/v1/events/{id}",
                HttpMethod.PUT,
                request,
                CreatedEventOverviewDTO.class,
                event.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo(validUpdateEventDTO.getTitle());
    }

    @Test
    void getEventById_ValidId_ReturnsOk() {
        ResponseEntity<CreatedEventOverviewDTO> response = restTemplate.getForEntity(
                "/api/v1/events/{id}",
                CreatedEventOverviewDTO.class,
                event.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(event.getId());
    }

    @Test
    void getEventById_InvalidId_ReturnsNotFound() {
        ResponseEntity<CreatedEventOverviewDTO> response = restTemplate.getForEntity(
                "/api/v1/events/{id}",
                CreatedEventOverviewDTO.class,
                999
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getAgenda_ValidEvent_ReturnsOk() {
        ResponseEntity<List> response = restTemplate.exchange(
                "/api/v1/events/{id}/agenda",
                HttpMethod.GET,
                null,
                List.class,
                event.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getAgenda_InvalidEvent_ReturnsNotFound() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/v1/events/{id}/agenda",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {},
                999
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateAgenda_ValidRequest_ReturnsCreated() {
        CreateActivityDTO validCreateActivityDTO = new CreateActivityDTO();
        validCreateActivityDTO.setTitle("New Activity");
        validCreateActivityDTO.setStartTime(LocalTime.now());
        validCreateActivityDTO.setEndTime(LocalTime.now().plusHours(1));
        validCreateActivityDTO.setAddress(new AddressDTO());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateActivityDTO> request = new HttpEntity<>(validCreateActivityDTO, headers);

        ResponseEntity<CreatedActivityDTO> response = restTemplate.exchange(
                "/api/v1/events/{id}/agenda",
                HttpMethod.PUT,
                request,
                CreatedActivityDTO.class,
                event.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo(validCreateActivityDTO.getTitle());
    }

    @Test
    void updateAgenda_InvalidEvent_ReturnsNotFound() {
        CreateActivityDTO validCreateActivityDTO = new CreateActivityDTO();
        validCreateActivityDTO.setTitle("New Activity");
        validCreateActivityDTO.setStartTime(LocalTime.now());
        validCreateActivityDTO.setEndTime(LocalTime.now().plusHours(1));
        validCreateActivityDTO.setAddress(new AddressDTO());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateActivityDTO> request = new HttpEntity<>(validCreateActivityDTO, headers);

        ResponseEntity<CreatedActivityDTO> response = restTemplate.exchange(
                "/api/v1/events/{id}/agenda",
                HttpMethod.PUT,
                request,
                CreatedActivityDTO.class,
                999
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteActivity_ValidRequest_ReturnsOk() {
        // Create an activity to delete
        CreateActivityDTO validCreateActivityDTO = new CreateActivityDTO();
        validCreateActivityDTO.setTitle("Activity to delete");
        validCreateActivityDTO.setStartTime(LocalTime.now());
        validCreateActivityDTO.setEndTime(LocalTime.now().plusHours(1));
        validCreateActivityDTO.setAddress(new AddressDTO());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateActivityDTO> request = new HttpEntity<>(validCreateActivityDTO, headers);
        ResponseEntity<CreatedActivityDTO> createResponse = restTemplate.exchange(
                "/api/v1/events/{id}/agenda",
                HttpMethod.PUT,
                request,
                CreatedActivityDTO.class,
                event.getId()
        );
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Now delete the activity
        int activityId = createResponse.getBody().getId();
        ResponseEntity<List> deleteResponse = restTemplate.exchange(
                "/api/v1/events/{eventId}/agenda/{activityId}",
                HttpMethod.DELETE,
                null,
                List.class,
                event.getId(),
                activityId
        );

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteActivity_InvalidEvent_ReturnsNotFound() {
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/v1/events/{eventId}/agenda/{activityId}",
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {},
                999,
                1
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
