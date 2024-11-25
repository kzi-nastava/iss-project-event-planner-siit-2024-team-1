package com.example.eventplanner.dto.merchandise.service;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationResponseDTO {
    private Long reservationId;
    private String serviceTitle;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String providerEmail;
}
