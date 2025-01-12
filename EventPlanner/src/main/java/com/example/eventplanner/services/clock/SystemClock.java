package com.example.eventplanner.services.clock;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class SystemClock implements Clock {
    @Override
    public Instant now() {
        return Instant.now();
    }

    @Override
    public LocalDateTime nowAsLocalDateTime() {
        return LocalDateTime.now();
    }

    @Override
    public LocalDate nowAsLocalDate() {
        return LocalDate.now();
    }
}