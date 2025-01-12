package com.example.eventplanner.merchandise;

import com.example.eventplanner.dto.merchandise.service.ReservationRequestDTO;
import com.example.eventplanner.dto.merchandise.service.ReservationResponseDTO;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Service;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.ServiceRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.isArray;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("jpatest")
class ServiceReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private ReservationRequestDTO validRequest;
    private Service service;
    private Event event;
    private EventOrganizer organizer;
    private ServiceProvider provider;
    private LocalDateTime eventDate;


    @BeforeEach
    void setUp() {
        eventDate = LocalDateTime.now().plusDays(7);

        // Create and save organizer
        organizer = new EventOrganizer();
        organizer.setUsername("organizer@test.com");
        organizer.setAddress(new Address());
        organizer.setNotifications(new ArrayList<>());
        organizer = userRepository.save(organizer);



        // Create and save event with budget
        event = new Event();
        event.setDate(eventDate);
        event.setAddress(new Address());
        event.setBudget(new Budget());
        event.getBudget().setBudgetItems(new ArrayList<>());
        event.setOrganizer(organizer);
        event = eventRepository.save(event);

        Category category = new Category();
        category.setPending(false);
        category.setTitle("Category");
        category.setDescription("Description");
        category=categoryRepository.save(category);

        // Create and save service
        service = new Service();
        service.setTitle("Test Service");
        service.setDescription("Test Description");
        service.setPrice(100.0);
        service.setAddress(new Address());
        service.setMinDuration(60);
        service.setMaxDuration(180);
        service.setReservationDeadline(1440);
        service.setTimeslots(new ArrayList<>());
        service.setCategory(category);
        service.setAvailable(true);
        service = serviceRepository.save(service);

        provider = new ServiceProvider();
        provider.setUsername("provider@test.com");
        provider.setMerchandise(Arrays.asList(service));
        provider.setAddress(new Address());
        provider = serviceProviderRepository.save(provider);

        validRequest = new ReservationRequestDTO();
        validRequest.setEventId(event.getId());
        validRequest.setStartTime(eventDate.minusHours(2));
        validRequest.setEndTime(eventDate.minusHours(1));
        validRequest.setOrganizerId(organizer.getId());
    }

    @Test
    void reserveService_ValidRequest_ReturnsOk() throws Exception {
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.serviceId").value(service.getId()))
                .andExpect(jsonPath("$.eventId").value(event.getId()));
    }

    @Test
    void reserveService_ServiceNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", 999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void reserveService_PastDate_ReturnsBadRequest() throws Exception {
        validRequest.setStartTime(LocalDateTime.now().minusDays(1));
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void reserveService_TimeSlotOverlap_ReturnsBadRequest() throws Exception {
        // First reservation
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());

        // Attempt to reserve the same timeslot
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void reserveService_DurationTooLong_ReturnsBadRequest() throws Exception {
        validRequest.setEndTime(validRequest.getStartTime().plusHours(4));
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void reserveService_DurationTooShort_ReturnsBadRequest() throws Exception {
        validRequest.setEndTime(validRequest.getStartTime());
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void reserveService_BeforeReservationDeadline_ReturnsBadRequest() throws Exception {
        validRequest.setStartTime(eventDate.minusMinutes(service.getReservationDeadline() + 1));
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void reserveService_EventNotFound_ReturnsNotFound() throws Exception {
        validRequest.setEventId(999);
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void reserveService_OrganizerNotFound_ReturnsBadRequest() throws Exception {
        validRequest.setOrganizerId(999);
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getServiceTimeslots_ValidService_ReturnsOk() throws Exception {
        mockMvc.perform(get("/api/v1/services/{serviceId}/timeslots", service.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getServiceTimeslots_ServiceNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/services/{serviceId}/timeslots", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    void reserveService_ExactlyAtReservationDeadline_Success() throws Exception {
        validRequest.setStartTime(eventDate.minusMinutes(service.getReservationDeadline()));
        validRequest.setEndTime(validRequest.getStartTime().plusMinutes(service.getMinDuration()));
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void reserveService_TimeslotBorderingExisting_Success() throws Exception {
        // First reservation
        validRequest.setStartTime(eventDate.minusHours(4));
        validRequest.setEndTime(eventDate.minusHours(3));
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());

        // Bordering reservation
        validRequest.setStartTime(eventDate.minusHours(3));
        validRequest.setEndTime(eventDate.minusHours(2));
        mockMvc.perform(post("/api/v1/services/{serviceId}/reserve", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());
    }
}