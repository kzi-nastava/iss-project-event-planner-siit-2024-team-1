package com.example.eventplanner.repositories.merchandise;

import com.example.eventplanner.model.merchandise.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeslotRepository extends JpaRepository<Timeslot, Integer> {
}
