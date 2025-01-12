package com.example.eventplanner.repositories.merchandise;

import com.example.eventplanner.model.merchandise.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    @Query("SELECT t FROM Timeslot t WHERE t.startTime > :currentTime")
    List<Timeslot> findAllFutureTimeslots(LocalDateTime currentTime);
}
