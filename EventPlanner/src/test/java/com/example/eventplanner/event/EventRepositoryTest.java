package com.example.eventplanner.repositories.event;

import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.common.Review;
import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.repositories.review.ReviewRepository;
import com.example.eventplanner.repositories.user.EventOrganizerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("jpatest")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private Event event1;
    private Event event2;
    private EventOrganizer organizer;
    private Review review1;
    private Review review2;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        // Create EventOrganizer
        organizer = new EventOrganizer();
        organizer.setName("Test Doe");
        organizer.setAddress(new Address());
        organizer = eventOrganizerRepository.save(organizer);

        // Create Reviews
        review1 = new Review();
        review1.setRating(4);
        review1.setComment("Great event!");
        review1 = reviewRepository.save(review1);

        review2 = new Review();
        review2.setRating(5);
        review2.setComment("Amazing experience!");
        review2 = reviewRepository.save(review2);

        // Create Events
        event1 = new Event();
        event1.setTitle("Tech Conference");
        event1.setDescription("A conference for tech enthusiasts.");
        event1.setMaxParticipants(100);
        event1.setPublic(true);
        event1.setDate(LocalDateTime.now().plusDays(10));
        event1.setMaxBudget(10000.0);
        event1.setAddress(new Address());
        event1.setReviews(List.of(review1));
        event1.setOrganizer(organizer);
        event1.setBudget(new Budget());
        event1.setParticipants(new ArrayList<>());

        event2 = new Event();
        event2.setTitle("Art Expo");
        event2.setDescription("An exhibition of modern art.");
        event2.setMaxParticipants(50);
        event2.setPublic(false);
        event2.setDate(LocalDateTime.now().plusDays(20));
        event2.setMaxBudget(5000.0);
        event2.setAddress(new Address());
        event2.setReviews(List.of(review2));
        event2.setOrganizer(organizer);
        event2.setBudget(new Budget());
        event2.setParticipants(new ArrayList<>());
    }

    @Test
    void findByOrganizerId_WithExistingOrganizer_ReturnsEvents() {
        // Save events
        eventRepository.save(event1);
        eventRepository.save(event2);

        // Retrieve events by organizer ID
        Pageable pageable = PageRequest.of(0, 10);
        Page<Event> eventsPage = eventRepository.findByOrganizerId(organizer.getId(), pageable);

        // Assertions
        assertEquals(2, eventsPage.getTotalElements());
        List<Event> events = eventsPage.getContent();
        assertTrue(events.contains(event1));
        assertTrue(events.contains(event2));
    }

    @Test
    void findByOrganizerId_WithNonExistentOrganizer_ReturnsEmptyPage() {
        // Retrieve events for a non-existent organizer ID
        Pageable pageable = PageRequest.of(0, 10);
        Page<Event> eventsPage = eventRepository.findByOrganizerId(-1, pageable);

        // Assertions
        assertTrue(eventsPage.isEmpty());
    }

    @Test
    void findByReviewsContaining_WithExistingReview_ReturnsEvent() {
        // Save events
        eventRepository.save(event1);

        // Retrieve event by review
        Event foundEvent = eventRepository.findByReviewsContaining(review1);

        // Assertions
        assertNotNull(foundEvent);
        assertEquals(event1.getId(), foundEvent.getId());
    }

    @Test
    void findByReviewsContaining_WithNonExistentReview_ReturnsNull() {
        // Save event
        eventRepository.save(event1);

        // Create a placeholder review with a non-existent ID
        Review nonExistentReview = new Review();
        nonExistentReview.setId(-1); // Ensure this ID does not exist in the database

        // Retrieve event by non-existent review
        Event foundEvent = eventRepository.findByReviewsContaining(nonExistentReview);

        // Assertions
        assertNull(foundEvent);
    }
}