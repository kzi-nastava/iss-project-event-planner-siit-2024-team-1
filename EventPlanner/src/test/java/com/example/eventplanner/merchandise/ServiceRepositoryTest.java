package com.example.eventplanner.merchandise;

import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Service;
import com.example.eventplanner.model.merchandise.Timeslot;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.ServiceRepository;
import com.example.eventplanner.repositories.merchandise.TimeslotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ActiveProfiles("jpatest")
class ServiceRepositoryTest {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CategoryRepository categoryRepository;  // You'll need to inject this

    @Autowired
    private TimeslotRepository timeslotRepository;  // And this

    private Service service1;
    private Service service2;
    private Category category1;
    private Category category2;
    private Timeslot timeslot1;
    private Timeslot timeslot2;
    private Event event;
    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        // Create categories
        category1 = new Category();
        category1.setTitle("Category 1");
        category1 = categoryRepository.save(category1);

        category2 = new Category();
        category2.setTitle("Category 2");
        category2 = categoryRepository.save(category2);

        // Create timeslots
        event = new Event();
        event.setBudget(new Budget());
        event.getBudget().setBudgetItems(new ArrayList<>());
        event.setAddress(new Address());
        event=eventRepository.save(event);
        timeslot1 = new Timeslot(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                event
        );
        timeslot1 = timeslotRepository.save(timeslot1);

        timeslot2 = new Timeslot(
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusHours(2),
                event
        );
        timeslot2 = timeslotRepository.save(timeslot2);

        // Create services
        service1 = new Service();
        service1.setMinDuration(60);
        service1.setMaxDuration(180);
        service1.setReservationDeadline(1440);
        service1.setTimeslots(new ArrayList<>());
        service1.setAddress(new Address());
        service1.setAvailable(true);
        service1.setDeleted(false);
        service1.setCategory(category1);
        service1.getTimeslots().add(timeslot1);

        service2 = new Service();
        service2.setMinDuration(120);
        service2.setMaxDuration(240);
        service2.setReservationDeadline(720);
        service2.setTimeslots(new ArrayList<>());
        service2.setAddress(new Address());
        service2.setAvailable(true);
        service2.setDeleted(false);
        service2.setCategory(category2);
        service2.getTimeslots().add(timeslot2);
    }

    // Tests for findAvailableServiceById
    @Test
    void findAvailableServiceById_ExistingAndAvailableService_ReturnsService() {
        Service savedService = serviceRepository.save(service1);

        Optional<Service> found = serviceRepository.findAvailableServiceById(savedService.getId());

        assertTrue(found.isPresent());
        assertEquals(savedService.getId(), found.get().getId());
    }

    @Test
    void findAvailableServiceById_UnavailableService_ReturnsEmpty() {
        service1.setAvailable(false);
        Service savedService = serviceRepository.save(service1);

        Optional<Service> found = serviceRepository.findAvailableServiceById(savedService.getId());

        assertTrue(found.isEmpty());
    }

    @Test
    void findAvailableServiceById_DeletedService_ReturnsEmpty() {
        service1.setDeleted(true);
        Service savedService = serviceRepository.save(service1);

        Optional<Service> found = serviceRepository.findAvailableServiceById(savedService.getId());

        assertTrue(found.isEmpty());
    }

    // Tests for findAllByCategories
    @Test
    void findAllByCategories_ExistingCategories_ReturnsServices() {
        serviceRepository.save(service1);
        serviceRepository.save(service2);

        List<Service> found = serviceRepository.findAllByCategories(
                Arrays.asList(category1.getId(), category2.getId())
        );

        assertEquals(2, found.size());
        assertTrue(found.stream().anyMatch(s -> s.getCategory().getId()==category1.getId()));
        assertTrue(found.stream().anyMatch(s -> s.getCategory().getId()==category2.getId()));
    }

    @Test
    void findAllByCategories_UnavailableServices_NotReturned() {
        service1.setAvailable(false);
        serviceRepository.save(service1);
        serviceRepository.save(service2);

        List<Service> found = serviceRepository.findAllByCategories(
                Arrays.asList(category1.getId(), category2.getId())
        );

        assertEquals(1, found.size());
        assertEquals(category2.getId(), found.get(0).getCategory().getId());
    }

    @Test
    void findAllByCategories_DeletedServices_NotReturned() {
        service1.setDeleted(true);
        serviceRepository.save(service1);
        serviceRepository.save(service2);

        List<Service> found = serviceRepository.findAllByCategories(
                Arrays.asList(category1.getId(), category2.getId())
        );

        assertEquals(1, found.size());
        assertEquals(category2.getId(), found.get(0).getCategory().getId());
    }

    // Tests for findByTimeslotsContaining
    @Test
    void findByTimeslotsContaining_ExistingTimeslot_ReturnsServices() {
        Service savedService = serviceRepository.save(service1);

        List<Service> found = serviceRepository.findByTimeslotsContaining(timeslot1);

        assertEquals(1, found.size());
        assertEquals(savedService.getId(), found.get(0).getId());
    }

    @Test
    void findByTimeslotsContaining_WithTimeslot_ReturnsService() {
        // Save service with timeslot
        Service savedService = serviceRepository.save(service1);

        // Verify the service can be found by its timeslot
        List<Service> found = serviceRepository.findByTimeslotsContaining(timeslot1);

        assertEquals(1, found.size());
        assertEquals(savedService.getId(), found.get(0).getId());
        assertTrue(found.get(0).getTimeslots().contains(timeslot1));
    }

    @Test
    void findByTimeslotsContaining_DifferentTimeslots_ReturnsCorrectService() {
        // Save two services with different timeslots
        Service savedService1 = serviceRepository.save(service1);  // has timeslot1
        Service savedService2 = serviceRepository.save(service2);  // has timeslot2

        // Find service by first timeslot
        List<Service> foundWithTimeslot1 = serviceRepository.findByTimeslotsContaining(timeslot1);
        assertEquals(1, foundWithTimeslot1.size());
        assertEquals(savedService1.getId(), foundWithTimeslot1.get(0).getId());

        // Find service by second timeslot
        List<Service> foundWithTimeslot2 = serviceRepository.findByTimeslotsContaining(timeslot2);
        assertEquals(1, foundWithTimeslot2.size());
        assertEquals(savedService2.getId(), foundWithTimeslot2.get(0).getId());
    }

    @Test
    void findByTimeslotsContaining_NonExistentTimeslot_ReturnsEmptyList() {
        // Save a service
        serviceRepository.save(service1);

        // Create a new unsaved timeslot
        Timeslot nonExistentTimeslot = new Timeslot(
                LocalDateTime.now().plusDays(3),
                LocalDateTime.now().plusDays(3).plusHours(2),
                event
        );
        timeslotRepository.save(nonExistentTimeslot);

        // Try to find service with unsaved timeslot
        List<Service> found = serviceRepository.findByTimeslotsContaining(nonExistentTimeslot);

        assertTrue(found.isEmpty());
    }


}