package com.example.eventplanner.dto.merchandise.service;

import com.example.eventplanner.dto.merchandise.MerchandisePhotoDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import lombok.Data;

import java.util.List;

@Data
public class ServiceOverviewDTO {
    private String title;
    private String description;
    private String specificity;
    private double price;
    private int discount;
    private boolean available;
    private List<EventType> eventTypes;
    private Category category;
    private List<MerchandisePhotoDTO> photos;
}
