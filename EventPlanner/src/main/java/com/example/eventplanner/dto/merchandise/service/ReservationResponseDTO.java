package com.example.eventplanner.dto.merchandise.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponseDTO {
    private int providerId;
    private int eventId;
    private int serviceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String providerEmail;
}
