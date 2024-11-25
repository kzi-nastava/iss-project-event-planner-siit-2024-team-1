package com.example.eventplanner.dto.service;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ReservationRequestDTO {
    @NotNull
    private Long eventId;

    @NotNull
    @FutureOrPresent
    private LocalDateTime startTime;

    private LocalDateTime endTime;  // Optional for fixed duration services
}
