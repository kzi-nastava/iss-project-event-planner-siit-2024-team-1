package com.example.eventplanner.dto.merchandise.service;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class ReservationRequestDTO {
    @NotNull
    private int eventId;
    @NotNull
    private int providerId;
    @NotNull
    @FutureOrPresent
    private LocalDateTime startTime;
    private String providerEmail;
    private LocalDateTime endTime;  // Optional for fixed duration services
}
