package com.example.eventplanner.dto.merchandise.service.update;

import lombok.Data;

import java.util.List;
@Data
public class UpdateServiceRequestDTO {
    private String title;
    private String description;
    private String specificity;
    private double price;
    private double discount;
    private List<String> eventType;
    private int minDuration;
    private int maxDuration;
    private int duration;
    private int reservationDeadline;
    private int cancellationDeadline;
    private boolean automaticReservation;
    private boolean visible;
    private boolean available;
    private String image;
}
