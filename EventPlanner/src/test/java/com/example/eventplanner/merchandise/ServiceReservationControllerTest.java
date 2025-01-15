package com.example.eventplanner.merchandise;

import com.example.eventplanner.dto.merchandise.service.ReservationRequestDTO;
import com.example.eventplanner.dto.merchandise.service.ReservationResponseDTO;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Service;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.ServiceRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("jpatest")
class ServiceReservationControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

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
        organizer.setName("Organizer");
        organizer.setUsername("organizer@test.com");
        organizer.setAddress(new Address());
        organizer.setNotifications(new ArrayList<>());
        organizer = userRepository.save(organizer);

        // Create and save event with budget
        event = new Event();
        event.setDate(eventDate);
        event.setTitle("Test Event");
        Address address = new Address();
        address.setCity("City");
        address.setStreet("Street");
        address.setNumber("1");
        event.setAddress(address);
        event.setBudget(new Budget());
        event.getBudget().setBudgetItems(new ArrayList<>());
        event.setOrganizer(organizer);
        event = eventRepository.save(event);

        Category category = new Category();
        category.setPending(false);
        category.setTitle("Category");
        category.setDescription("Description");
        category = categoryRepository.save(category);

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
        provider.setName("Provider");
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
    void reserveService_ValidRequest_ReturnsOk() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservationRequestDTO> request = new HttpEntity<>(validRequest, headers);

        ResponseEntity<ReservationResponseDTO> response = restTemplate.postForEntity(
                "/api/v1/services/{serviceId}/reserve",
                request,
                ReservationResponseDTO.class,
                service.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getServiceId()).isEqualTo(service.getId());
        assertThat(response.getBody().getEventId()).isEqualTo(event.getId());
    }

    @Test
    void reserveService_ServiceNotFound_ReturnsNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservationRequestDTO> request = new HttpEntity<>(validRequest, headers);

        ResponseEntity<ReservationResponseDTO> response = restTemplate.postForEntity(
                "/api/v1/services/{serviceId}/reserve",
                request,
                ReservationResponseDTO.class,
                999
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void reserveService_PastDate_ReturnsBadRequest() {
        validRequest.setStartTime(LocalDateTime.now().minusDays(1));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservationRequestDTO> request = new HttpEntity<>(validRequest, headers);

        ResponseEntity<ReservationResponseDTO> response = restTemplate.postForEntity(
                "/api/v1/services/{serviceId}/reserve",
                request,
                ReservationResponseDTO.class,
                service.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void reserveService_TimeSlotOverlap_ReturnsBadRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservationRequestDTO> request = new HttpEntity<>(validRequest, headers);

        // First reservation
        restTemplate.postForEntity(
                "/api/v1/services/{serviceId}/reserve",
                request,
                ReservationResponseDTO.class,
                service.getId()
        );

        // Attempt to reserve the same timeslot
        ResponseEntity<ReservationResponseDTO> response = restTemplate.postForEntity(
                "/api/v1/services/{serviceId}/reserve",
                request,
                ReservationResponseDTO.class,
                service.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getServiceTimeslots_ValidService_ReturnsOk() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/api/v1/services/{serviceId}/timeslots",
                List.class,
                service.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void getServiceTimeslots_ServiceNotFound_ReturnsNotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/api/v1/services/{serviceId}/timeslots",
                String.class,
                999
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void reserveService_ExactlyAtReservationDeadline_Success() {
        validRequest.setStartTime(eventDate.minusMinutes(service.getReservationDeadline()));
        validRequest.setEndTime(validRequest.getStartTime().plusMinutes(service.getMinDuration()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReservationRequestDTO> request = new HttpEntity<>(validRequest, headers);

        ResponseEntity<ReservationResponseDTO> response = restTemplate.postForEntity(
                "/api/v1/services/{serviceId}/reserve",
                request,
                ReservationResponseDTO.class,
                service.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void reserveService_TimeslotBorderingExisting_Success() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // First reservation
        validRequest.setStartTime(eventDate.minusHours(4));
        validRequest.setEndTime(eventDate.minusHours(3));
        HttpEntity<ReservationRequestDTO> firstRequest = new HttpEntity<>(validRequest, headers);

        ResponseEntity<ReservationResponseDTO> firstResponse = restTemplate.postForEntity(
                "/api/v1/services/{serviceId}/reserve",
                firstRequest,
                ReservationResponseDTO.class,
                service.getId()
        );

        assertThat(firstResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Bordering reservation
        validRequest.setStartTime(eventDate.minusHours(3));
        validRequest.setEndTime(eventDate.minusHours(2));
        HttpEntity<ReservationRequestDTO> secondRequest = new HttpEntity<>(validRequest, headers);

        ResponseEntity<ReservationResponseDTO> secondResponse = restTemplate.postForEntity(
                "/api/v1/services/{serviceId}/reserve",
                secondRequest,
                ReservationResponseDTO.class,
                service.getId()
        );

        assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}