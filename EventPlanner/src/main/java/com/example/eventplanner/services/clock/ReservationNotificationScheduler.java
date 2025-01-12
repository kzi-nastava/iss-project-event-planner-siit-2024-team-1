package com.example.eventplanner.services.clock;

import com.example.eventplanner.dto.merchandise.service.ReservationResponseDTO;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Timeslot;
import com.example.eventplanner.repositories.merchandise.ServiceRepository;
import com.example.eventplanner.repositories.merchandise.TimeslotRepository;
import com.example.eventplanner.services.notification.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@EnableScheduling
@Slf4j
public class ReservationNotificationScheduler {
    private final NotificationService notificationService;
    private final Clock clock;
    private final Map<Long, ScheduledFuture<?>> scheduledNotifications = new ConcurrentHashMap<>();
    private final TaskScheduler taskScheduler;
    private final TimeslotRepository timeslotRepository;
    private final ServiceRepository serviceRepository;

    @Autowired
    public ReservationNotificationScheduler(
            NotificationService notificationService,
            Clock clock,
            TaskScheduler taskScheduler,
            TimeslotRepository timeslotRepository,
            ServiceRepository serviceRepository) {
        this.notificationService = notificationService;
        this.clock = clock;
        this.taskScheduler = taskScheduler;
        this.timeslotRepository = timeslotRepository;
        this.serviceRepository = serviceRepository;
    }

    @PostConstruct
    public void initializeNotifications() {
        log.info("Initializing notifications for all future timeslots");
        LocalDateTime currentTime = clock.nowAsLocalDateTime();

        List<Timeslot> futureTimeslots = timeslotRepository.findAllFutureTimeslots(currentTime);

        for (Timeslot timeslot : futureTimeslots) {
            // Find the service that contains this timeslot
            List<com.example.eventplanner.model.merchandise.Service> services = serviceRepository.findByTimeslotsContaining(timeslot);

            for (com.example.eventplanner.model.merchandise.Service service : services) {
                try {
                    scheduleReservationNotification(service, timeslot);
                    log.info("Scheduled notification for service {} and timeslot {} at {}",
                            service.getId(), timeslot.getId(), timeslot.getStartTime());
                } catch (Exception e) {
                    log.error("Failed to schedule notification for service {} and timeslot {}",
                            service.getId(), timeslot.getId(), e);
                }
            }
        }

        log.info("Completed initializing notifications for {} future timeslots",
                futureTimeslots.size());
    }

    public void scheduleReservationNotification(com.example.eventplanner.model.merchandise.Service service, Timeslot timeslot) {
        if (timeslot==null || timeslot.getStartTime() == null) {
            log.warn("Start time is null for reservation {}", service.getId());
            return;
        }

        LocalDateTime scheduledNotificationTime = timeslot.getStartTime().minusHours(1);
        LocalDateTime currentTime = clock.nowAsLocalDateTime();

        if (scheduledNotificationTime.isBefore(currentTime)) {
            log.info("Notification time has passed, sending immediate notification for service {}",
                    service.getId());
            sendReservationNotifications(service, timeslot);
            return;
        }

        log.info("Scheduling notification for service {} at {}",
                service.getId(), scheduledNotificationTime);

        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(
                () -> sendReservationNotifications(service,timeslot),
                scheduledNotificationTime.atZone(ZoneId.systemDefault()).toInstant()
        );

        ScheduledFuture<?> existingTask = scheduledNotifications.put(timeslot.getId(), scheduledTask);
        if (existingTask != null && !existingTask.isDone()) {
            existingTask.cancel(false);
            log.info("Cancelled existing notification for service {}", service.getId());
        }
    }

    private void sendReservationNotifications(com.example.eventplanner.model.merchandise.Service service,Timeslot timeslot) {
        try {
            notificationService.notifyOrganizerOfReservation(service, timeslot);
            scheduledNotifications.remove(timeslot.getId());
            log.info("Successfully sent notification for service {}", service.getId());
        } catch (Exception e) {
            log.error("Failed to send reservation notification for service {}",
                    service.getId(), e);
        }
    }
}
