package com.example.eventplanner.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.*;
import com.example.eventplanner.exceptions.EventException;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Activity;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.budget.BudgetItemRepository;
import com.example.eventplanner.repositories.budget.BudgetRepository;
import com.example.eventplanner.repositories.event.ActivityRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.eventType.EventTypeRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.repositories.user.EventOrganizerRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import com.example.eventplanner.services.event.EventService;
import com.example.eventplanner.services.notification.NotificationService;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventTypeRepository eventTypeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventOrganizerRepository eventOrganizerRepository;

    @Mock
    private BudgetRepository budgetRepository;

    @Mock
    private BudgetItemRepository budgetItemRepository;

    @Mock
    private MerchandiseRepository merchandiseRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private EventService eventService;

    private EventOrganizer organizer;
    private Event event;
    private CreateEventDTO validCreateRequest;
    private CreateEventDTO invalidCreateRequest;
    private UpdateEventDTO validUpdateRequest;
    private UpdateEventDTO invalidUpdateRequest;
    private EventType eventType;
    private User user;
    private CreateActivityDTO updateAgendaRequest;

    @BeforeEach
    void setUp() {
        organizer = new EventOrganizer();
        organizer.setId(1);
        organizer.setUsername("organizer@test.com");
        organizer.setOrganizingEvents(new ArrayList<>());

        eventType = new EventType();
        eventType.setId(1);
        eventType.setTitle("Test Event Type");
        eventType.setDescription("Test Event Type");
        eventType.setActive(true);
        eventType.setCategories(new ArrayList<>());

        // Mock the repository to return the created event type when findById is called
        lenient().when(eventTypeRepository.findById(1)).thenReturn(Optional.of(eventType));

        event = new Event();
        event.setId(1);
        event.setTitle("Test Event");
        event.setOrganizer(organizer);
        event.setDate(LocalDateTime.now().plusDays(7));
        event.setMaxParticipants(100);
        event.setType(eventType);
        event.setDescription("A test event.");
        event.setAddress(new Address());

        List<Activity> activities = new ArrayList<>();
        activities.add(new Activity(1, "Activity", "Test", LocalTime.now(), LocalTime.now(), new Address()));
        event.setActivities(activities);

        validCreateRequest = new CreateEventDTO();
        validCreateRequest.setTitle("Test Event");
        validCreateRequest.setOrganizerId(1);
        validCreateRequest.setEventTypeId(1);
        validCreateRequest.setDate(LocalDateTime.now().plusDays(7));
        validCreateRequest.setMaxParticipants(100);
        validCreateRequest.setDescription("A test event.");
        validCreateRequest.setAddress(new AddressDTO());

        invalidCreateRequest = new CreateEventDTO();
        invalidCreateRequest.setTitle("");
        invalidCreateRequest.setOrganizerId(1);
        invalidCreateRequest.setDate(LocalDateTime.now().minusDays(1));  // Invalid date (past)
        invalidCreateRequest.setMaxParticipants(100);
        invalidCreateRequest.setEventTypeId(1);
        invalidCreateRequest.setDescription("A test event with an invalid date.");
        invalidCreateRequest.setAddress(new AddressDTO());

        validUpdateRequest = new UpdateEventDTO();
        validUpdateRequest.setTitle("Test Event");
        validUpdateRequest.setDate(LocalDateTime.now().plusDays(7));
        validUpdateRequest.setMaxParticipants(100);
        validUpdateRequest.setEventTypeId(1);
        validUpdateRequest.setDescription("A test event.");
        validUpdateRequest.setAddress(new AddressDTO());
        validUpdateRequest.setPublic(true);
        validUpdateRequest.setProductIds(new ArrayList<>());
        validUpdateRequest.setServiceIds(new ArrayList<>());

        invalidUpdateRequest = new UpdateEventDTO();
        invalidUpdateRequest.setTitle("");
        invalidUpdateRequest.setDate(LocalDateTime.now().minusDays(1));  // Invalid date (past)
        invalidUpdateRequest.setMaxParticipants(100);
        invalidUpdateRequest.setDescription("A test event with an invalid date.");
        invalidUpdateRequest.setAddress(new AddressDTO());
        invalidUpdateRequest.setEventTypeId(1);
    }

    @Test
    void createEvent_ValidRequest_Success() {
        when(eventOrganizerRepository.findById(1)).thenReturn(Optional.of(organizer));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        CreatedEventOverviewDTO response = eventService.createEvent(validCreateRequest);

        assertNotNull(response);
        assertEquals(validCreateRequest.getTitle(), response.getTitle());
        assertEquals(validCreateRequest.getMaxParticipants(), response.getMaxParticipants());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void createEvent_InvalidRequest_ThrowsException() {
        lenient().when(userRepository.findById(1)).thenReturn(Optional.of(organizer));

        EventException exception = assertThrows(
                EventException.class,
                () -> eventService.createEvent(invalidCreateRequest)
        );
        assertEquals(EventException.ErrorType.INVALID_DATE, exception.getErrorType());
    }

    @Test
    void createEvent_OrganizerNotFound_ThrowsException() {
        when(eventOrganizerRepository.findById(1)).thenReturn(Optional.empty());

        EventException exception = assertThrows(
                EventException.class,
                () -> eventService.createEvent(validCreateRequest)
        );
        assertEquals(EventException.ErrorType.ORGANIZER_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void updateEvent_ValidRequest_Success() {
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        validUpdateRequest.setTitle("Updated Event Title");
        CreatedEventOverviewDTO response = eventService.updateEvent(1, validUpdateRequest);

        assertNotNull(response);
        assertEquals("Updated Event Title", response.getTitle());
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    void updateEvent_EventNotFound_ThrowsException() {
        when(eventRepository.findById(1)).thenReturn(Optional.empty());

        EventException exception = assertThrows(
                EventException.class,
                () -> eventService.updateEvent(1, validUpdateRequest)
        );
        assertEquals(EventException.ErrorType.EVENT_NOT_FOUND, exception.getErrorType());
    }

    @Test
    void updateAgenda_ValidRequest_Success() {
        // Prepare CreateActivityDTO with valid data
        CreateActivityDTO activityDTO = new CreateActivityDTO();
        activityDTO.setTitle("New Activity");
        activityDTO.setDescription("A description for the new activity");
        activityDTO.setStartTime(LocalTime.now().plusHours(1));
        activityDTO.setEndTime(LocalTime.now().plusHours(2));
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("123 Street");
        addressDTO.setCity("City");
        addressDTO.setNumber("12");
        addressDTO.setLatitude(0.0);
        addressDTO.setLongitude(0.0);
        activityDTO.setAddress(addressDTO);

        // Mock the event and activity repository behavior
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        // Mock saving the activity and return an Activity with the title "New Activity"
        Activity savedActivity = new Activity();
        savedActivity.setId(1); // Assuming the saved Activity gets an ID
        savedActivity.setTitle("New Activity");
        savedActivity.setDescription("A description for the new activity");
        savedActivity.setStartTime(activityDTO.getStartTime());
        savedActivity.setEndTime(activityDTO.getEndTime());
        Address address = new Address();
        address.setStreet("123 Street");
        address.setCity("City");
        address.setNumber("12");
        address.setLatitude(0.0);
        address.setLongitude(0.0);
        savedActivity.setAddress(address); // Ensure this matches your logic

        when(activityRepository.save(any(Activity.class))).thenReturn(savedActivity);

        // Call the method
        CreatedActivityDTO response = eventService.updateAgenda(1, activityDTO);

        // Verify results
        assertNotNull(response);
        assertEquals(activityDTO.getTitle(), response.getTitle());
        assertEquals(activityDTO.getDescription(), response.getDescription());
        assertEquals(activityDTO.getStartTime(), response.getStartTime());
        assertEquals(activityDTO.getEndTime(), response.getEndTime());
        assertEquals(activityDTO.getAddress().getStreet(), response.getAddress().getStreet());
        assertEquals(activityDTO.getAddress().getCity(), response.getAddress().getCity());
        verify(activityRepository).save(any(Activity.class));
        verify(eventRepository).save(any(Event.class)); // Verify the event repository save is called
    }

    @Test
    void updateAgenda_StartTimeAfterEndTime_ThrowsException() {
        // Prepare CreateActivityDTO with invalid time (start time after end time)
        CreateActivityDTO activityDTO = new CreateActivityDTO();
        activityDTO.setTitle("New Activity");
        activityDTO.setDescription("A description for the new activity");
        activityDTO.setStartTime(LocalTime.now().plusHours(3));
        activityDTO.setEndTime(LocalTime.now().plusHours(2));  // End time is before start time
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("123 Street");
        addressDTO.setCity("City");
        addressDTO.setNumber("12");
        addressDTO.setLatitude(0.0);
        addressDTO.setLongitude(0.0);
        activityDTO.setAddress(addressDTO);
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));
        // Call the method and expect an exception
        EventException exception = assertThrows(
                EventException.class,
                () -> eventService.updateAgenda(1, activityDTO)
        );

        assertEquals(EventException.ErrorType.INVALID_ACTIVITY_TIME, exception.getErrorType());
    }

    @Test
    void getAgenda_ValidEvent_Success() {
        // Prepare the mock behavior
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        // Call the method
        List<ActivityOverviewDTO> response = eventService.getAgenda(1);

        // Verify results
        assertNotNull(response);
        assertFalse(response.isEmpty()); // Ensure there are activities returned
        verify(eventRepository).findById(1);
    }

    @Test
    void getAgenda_EventNotFound_ThrowsException() {
        // Mock the eventRepository to return empty
        when(eventRepository.findById(1)).thenReturn(Optional.empty());

        // Call the method and verify exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.getAgenda(1));
        assertEquals("Event not found", exception.getMessage());
    }

    @Test
    void getActivity_ValidActivity_Success() {
        Activity activity = new Activity();
        activity.setId(1);
        activity.setTitle("Test Activity");
        activity.setDescription("Test Description");
        activity.setAddress(new Address());

        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        // Call the method
        ActivityOverviewDTO response = eventService.getActivity(1);

        // Verify results
        assertNotNull(response);
        assertEquals(activity.getTitle(), response.getTitle());
        assertEquals(activity.getDescription(), response.getDescription());
        verify(activityRepository).findById(1);
    }

    @Test
    void getActivity_ActivityNotFound_ThrowsException() {
        // Mock activity repository to return empty
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        // Call the method and verify exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.getActivity(1));
        assertEquals("Activity not found", exception.getMessage());
    }

    @Test
    void updateActivity_ValidRequest_Success() {
        CreateActivityDTO activityDTO = new CreateActivityDTO();
        activityDTO.setTitle("Updated Activity");
        activityDTO.setDescription("Updated Description");
        activityDTO.setStartTime(LocalTime.now().plusHours(1));
        activityDTO.setEndTime(LocalTime.now().plusHours(2));
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Updated Street");
        activityDTO.setAddress(addressDTO);

        Activity existingActivity = new Activity();
        existingActivity.setId(1);
        existingActivity.setTitle("Old Activity");

        // Mock the repository behavior
        when(activityRepository.findById(1)).thenReturn(Optional.of(existingActivity));
        when(activityRepository.save(any(Activity.class))).thenReturn(existingActivity);

        // Call the method
        ActivityOverviewDTO response = eventService.updateActivity(1, activityDTO);

        // Verify results
        assertNotNull(response);
        assertEquals(activityDTO.getTitle(), response.getTitle());
        verify(activityRepository).save(any(Activity.class)); // Verify the save method was called
    }

    @Test
    void updateActivity_ActivityNotFound_ThrowsException() {
        // Mock the activity repository to return empty
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        // Call the method and verify exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> eventService.updateActivity(1, updateAgendaRequest));
        assertEquals("Activity not found", exception.getMessage());
    }

    @Test
    void deleteActivity_ValidRequest_Success() {
        Activity activity = new Activity();
        activity.setId(1);
        activity.setTitle("Test Activity");

        // Mock event and activity repositories
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));
        lenient().when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        // Call the method
        List<ActivityOverviewDTO> response = eventService.deleteActivity(1, 1);

        // Verify results
        assertNotNull(response);
        assertTrue(response.isEmpty()); // Ensure the activity is removed
        verify(eventRepository).save(any(Event.class));
        verify(activityRepository).deleteById(1); // Verify the activity is deleted
    }

    @Test
    void deleteActivity_ActivityNotFound_ThrowsException() {
        // Mock the event repository to return an event
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));
        // Mock the activity repository to return empty
        lenient().when(activityRepository.findById(1)).thenReturn(Optional.empty());

        // Call the method and verify exception
        RuntimeException exception = assertThrows(EventException.class, () -> eventService.deleteActivity(1, -1));
        assertEquals("Activity not found", exception.getMessage());
    }

    @Test
    void deleteActivity_EventNotFound_ThrowsException() {
        // Mock event repository to return empty
        when(eventRepository.findById(1)).thenReturn(Optional.empty());

        // Call the method and verify exception
        RuntimeException exception = assertThrows(EventException.class, () -> eventService.deleteActivity(1, 1));
        assertEquals("Event not found", exception.getMessage());
    }
}
