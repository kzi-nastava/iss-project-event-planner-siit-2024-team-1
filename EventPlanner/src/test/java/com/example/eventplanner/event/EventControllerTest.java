package com.example.eventplanner.event;

import com.example.eventplanner.dto.event.*;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.services.event.EventService;
import com.example.eventplanner.services.user.UserService;
import com.example.eventplanner.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private Event testEvent;

    @BeforeEach
    void setUp() {
        // Prepare event for testing
        testEvent = new Event();
        testEvent.setTitle("Test Event");
        testEvent.setDate(LocalDateTime.now().plusDays(7));
        eventRepository.save(testEvent);
    }

    @Test
    void createEvent_ValidRequest_ReturnsCreated() throws Exception {
        CreateEventDTO dto = new CreateEventDTO();
        dto.setTitle("New Event");
        dto.setDate(LocalDateTime.now().plusDays(10));

        mockMvc.perform(post("/api/v1/events")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Event"))
                .andExpect(jsonPath("$.date").value(LocalDate.now().plusDays(10).toString()));
    }

    @Test
    void updateEvent_ValidRequest_ReturnsUpdatedEvent() throws Exception {
        UpdateEventDTO dto = new UpdateEventDTO();
        dto.setTitle("Updated Test Event");
        dto.setDate(LocalDateTime.now().plusDays(8));

        mockMvc.perform(put("/api/v1/events/{id}", testEvent.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Test Event"))
                .andExpect(jsonPath("$.date").value(LocalDate.now().plusDays(8).toString()));
    }

    @Test
    void getEventById_ValidEvent_ReturnsEvent() throws Exception {
        mockMvc.perform(get("/api/v1/events/{id}", testEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Event"))
                .andExpect(jsonPath("$.date").value(testEvent.getDate().toString()));
    }

    @Test
    void getAgenda_ValidEvent_ReturnsAgenda() throws Exception {
        mockMvc.perform(get("/api/v1/events/{id}/agenda", testEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void updateAgenda_ValidRequest_ReturnsUpdatedActivity() throws Exception {
        CreateActivityDTO dto = new CreateActivityDTO();
        dto.setTitle("New Activity");
        dto.setDescription("Activity description");
        dto.setStartTime(LocalTime.now().plusHours(1));
        dto.setEndTime(LocalTime.now().plusHours(2));

        mockMvc.perform(put("/api/v1/events/{id}/agenda", testEvent.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Activity"))
                .andExpect(jsonPath("$.description").value("Activity description"));
    }

    @Test
    void updateActivity_ValidRequest_ReturnsUpdatedActivity() throws Exception {
        CreateActivityDTO dto = new CreateActivityDTO();
        dto.setTitle("Updated Activity");
        dto.setDescription("Updated description");
        dto.setStartTime(LocalTime.now().plusHours(1));
        dto.setEndTime(LocalTime.now().plusHours(2));

        mockMvc.perform(put("/api/v1/events/agenda/{activityId}", 1)  // Assuming activityId 1 exists
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Updated Activity"))
                .andExpect(jsonPath("$.description").value("Updated description"));
    }

    @Test
    void deleteAgenda_ValidRequest_ReturnsUpdatedAgenda() throws Exception {
        mockMvc.perform(delete("/api/v1/events/{eventId}/agenda/{activityId}", testEvent.getId(), 1))  // Assuming activityId 1 exists
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getActivity_ValidActivity_ReturnsActivity() throws Exception {
        mockMvc.perform(get("/api/v1/events/activities/{id}", 1))  // Assuming activityId 1 exists
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.description").exists());
    }

    @Test
    void getEventById_EventNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/events/{id}", 999))  // Non-existent event
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEvent_EventNotFound_ReturnsNotFound() throws Exception {
        UpdateEventDTO dto = new UpdateEventDTO();
        dto.setTitle("Updated Event");
        dto.setDate(LocalDateTime.now().plusDays(8));

        mockMvc.perform(put("/api/v1/events/{id}", 999)  // Non-existent event
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
