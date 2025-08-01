package com.example.eventplanner.dto.merchandise.service.create;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.merchandise.MerchandisePhotoDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import lombok.Data;

import java.util.List;
@Data
public class CreateServiceResponseDTO {
    private int id;
    private String title;
    private String description;
    private String specificity;
    private double price;
    private int discount;
    private boolean visible;
    private boolean available;
    private int minDuration;
    private int maxDuration;
    private int reservationDeadline;
    private int cancellationDeadline;
    private boolean automaticReservation;
    private List<MerchandisePhotoDTO> merchandisePhotos;
    private List<EventType> eventTypes;
    private AddressDTO address;
    private Category category;
    private int serviceProviderId;
}
