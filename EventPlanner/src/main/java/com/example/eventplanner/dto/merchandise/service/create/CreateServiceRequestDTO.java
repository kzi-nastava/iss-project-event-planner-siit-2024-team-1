package com.example.eventplanner.dto.merchandise.service.create;

import lombok.Data;

import java.util.List;

@Data
public class CreateServiceRequestDTO {
    private String title;
    private String description;
    private String specificity;
    private double price;
    private double discount;
    private String category;
    private List<String> types;
    private int minDuration;
    private int maxDuration;
    private int duration;
    private int reservationDeadline;
    private int cancellationDeadline;
    private boolean automaticReservation;
    private String image;

}
