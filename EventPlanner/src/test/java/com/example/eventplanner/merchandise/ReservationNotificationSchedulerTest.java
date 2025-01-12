package com.example.eventplanner.merchandise;

import com.example.eventplanner.dto.merchandise.service.ReservationResponseDTO;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Service;
import com.example.eventplanner.model.merchandise.Timeslot;
import com.example.eventplanner.repositories.merchandise.ServiceRepository;
import com.example.eventplanner.repositories.merchandise.TimeslotRepository;
import com.example.eventplanner.services.clock.Clock;
import com.example.eventplanner.services.clock.ReservationNotificationScheduler;
import com.example.eventplanner.services.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ScheduledFuture;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ScheduledFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationNotificationSchedulerTest {
    @Mock
    private NotificationService notificationService;
    @Mock
    private Clock clock;
    @Mock
    private TaskScheduler taskScheduler;
    @Mock
    private TimeslotRepository timeslotRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @Captor
    private ArgumentCaptor<Instant> scheduleTimeCaptor;

    @InjectMocks
    private ReservationNotificationScheduler scheduler;

    private Service service;
    private Event event;
    private Timeslot timeslot;
    private LocalDateTime currentTime;

    @BeforeEach
    void setUp() {
        currentTime = LocalDateTime.of(2024, 1, 1, 12, 0); // Fixed time for consistent testing

        service = new Service();
        service.setId(1);
        service.setTitle("Test Service");

        event = new Event();
        event.setId(1);
        event.setTitle("Test Event");

        timeslot = new Timeslot();
        timeslot.setId(1L);
        timeslot.setStartTime(currentTime.plusHours(2)); // Reservation starts in 2 hours

    }

    @Test
    void scheduleNotification_FutureTime_SchedulesCorrectly() {
        // Create ArgumentCaptor for Instant
        when(clock.nowAsLocalDateTime()).thenReturn(currentTime);

        ArgumentCaptor<Instant> scheduleTimeCaptor = ArgumentCaptor.forClass(Instant.class);

        // Mock the scheduler using raw type
        @SuppressWarnings("unchecked")
        ScheduledFuture mockFuture = mock(ScheduledFuture.class);
        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class)))
                .thenReturn(mockFuture);

        scheduler.scheduleReservationNotification(service, timeslot);

        verify(taskScheduler).schedule(any(Runnable.class), scheduleTimeCaptor.capture());

        Instant expectedScheduleTime = currentTime.plusHours(1)
                .atZone(ZoneId.systemDefault()).toInstant();
        assertEquals(expectedScheduleTime, scheduleTimeCaptor.getValue(),
                "Notification should be scheduled 1 hour before the reservation");

        verify(notificationService, never())
                .notifyOrganizerOfReservation(any(), any());
    }

    @Test
    void scheduleNotification_PastTime_SendsImmediately() {
        when(clock.nowAsLocalDateTime()).thenReturn(currentTime);

        timeslot.setStartTime(currentTime.minusMinutes(30));

        scheduler.scheduleReservationNotification(service,timeslot);

        verify(taskScheduler, never()).schedule(any(Runnable.class), any(Instant.class));
        verify(notificationService).notifyOrganizerOfReservation(service,timeslot);
    }

    @Test
    void scheduleNotification_ExactlyOneHourBefore_SendsImmediately() {
        when(clock.nowAsLocalDateTime()).thenReturn(currentTime);

        timeslot.setStartTime(currentTime.plusHours(1).minusMinutes(1));

        scheduler.scheduleReservationNotification(service,timeslot);

        verify(taskScheduler, never()).schedule(any(Runnable.class), any(Instant.class));
        verify(notificationService).notifyOrganizerOfReservation(service,timeslot);
    }

    @Test
    void scheduleNotification_NullStartTime_DoesNotSchedule() {
        timeslot.setStartTime(null);

        scheduler.scheduleReservationNotification(service,timeslot);

        verify(taskScheduler, never()).schedule(any(Runnable.class), any(Instant.class));
        verify(notificationService, never()).notifyOrganizerOfReservation(any(), any());
    }

    @Test
    void initializeNotifications_ShouldScheduleAllFutureTimeslots() {
        // Setup
        LocalDateTime currentTime = LocalDateTime.of(2024, 1, 1, 12, 0);
        when(clock.nowAsLocalDateTime()).thenReturn(currentTime);

        Timeslot timeslot1 = new Timeslot();
        timeslot1.setId(1L);
        timeslot1.setStartTime(currentTime.plusHours(2));

        Timeslot timeslot2 = new Timeslot();
        timeslot2.setId(2L);
        timeslot2.setStartTime(currentTime.plusHours(3));

        Service service = new Service();
        service.setId(1);

        when(timeslotRepository.findAllFutureTimeslots(currentTime))
                .thenReturn(Arrays.asList(timeslot1, timeslot2));
        when(serviceRepository.findByTimeslotsContaining(any()))
                .thenReturn(Collections.singletonList(service));
        when(taskScheduler.schedule(any(Runnable.class), any(Instant.class)))
                .thenReturn(mock(ScheduledFuture.class));

        // Execute
        scheduler.initializeNotifications();

        // Verify
        verify(taskScheduler, times(2)).schedule(any(Runnable.class), any(Instant.class));
        verify(timeslotRepository).findAllFutureTimeslots(currentTime);
        verify(serviceRepository, times(2)).findByTimeslotsContaining(any(Timeslot.class));
    }


}
