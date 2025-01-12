package com.example.eventplanner.merchandise;


import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Service;
import com.example.eventplanner.model.merchandise.Timeslot;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("jpatest")
class TimeslotRepositoryTest {
    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private EventRepository eventRepository;

    private final LocalDateTime baseTime = LocalDateTime.of(2024, 1, 1, 12, 0); // Fixed time
    private Timeslot pastTimeslot;
    private Timeslot currentTimeslot;
    private Timeslot futureTimeslot1;
    private Timeslot futureTimeslot2;
    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
        event.setBudget(new Budget());
        event.getBudget().setBudgetItems(new ArrayList<>());
        event.setAddress(new Address());
        event = eventRepository.save(event);

        // Create timeslot in the past (1 day before baseTime)
        pastTimeslot = new Timeslot(
                baseTime.minusDays(1),
                baseTime.minusDays(1).plusHours(2),
                event
        );

        // Create timeslot for current time
        currentTimeslot = new Timeslot(
                baseTime,
                baseTime.plusHours(2),
                event
        );

        // Create two future timeslots
        futureTimeslot1 = new Timeslot(
                baseTime.plusDays(1),
                baseTime.plusDays(1).plusHours(2),
                event
        );

        futureTimeslot2 = new Timeslot(
                baseTime.plusDays(2),
                baseTime.plusDays(2).plusHours(2),
                event
        );
    }

    @Test
    void findAllFutureTimeslots_ReturnsOnlyFutureTimeslots() {
        // Save all timeslots
        timeslotRepository.saveAll(Arrays.asList(pastTimeslot, currentTimeslot,
                futureTimeslot1, futureTimeslot2));

        // Query for future timeslots
        List<Timeslot> futureTimeslots = timeslotRepository
                .findAllFutureTimeslots(baseTime);

        // Verify results
        assertEquals(2, futureTimeslots.size());
        assertTrue(futureTimeslots.stream()
                .allMatch(t -> t.getStartTime().isAfter(baseTime)));
        assertTrue(futureTimeslots.contains(futureTimeslot1));
        assertTrue(futureTimeslots.contains(futureTimeslot2));
    }

    @Test
    void findAllFutureTimeslots_NoFutureTimeslots_ReturnsEmptyList() {
        // Save only past and current timeslots
        timeslotRepository.saveAll(Arrays.asList(pastTimeslot, currentTimeslot));

        // Query for future timeslots
        List<Timeslot> futureTimeslots = timeslotRepository
                .findAllFutureTimeslots(baseTime);

        // Verify results
        assertTrue(futureTimeslots.isEmpty());
    }
}