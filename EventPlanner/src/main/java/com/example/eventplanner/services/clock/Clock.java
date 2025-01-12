package com.example.eventplanner.services.clock;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Clock {
    Instant now();
    LocalDateTime nowAsLocalDateTime();
    LocalDate nowAsLocalDate();
}