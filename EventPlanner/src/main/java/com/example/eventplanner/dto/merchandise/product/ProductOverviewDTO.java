package com.example.eventplanner.dto.merchandise.product;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.merchandise.CreateMerchandisePhotoDTO;
import com.example.eventplanner.dto.merchandise.MerchandisePhotoDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductOverviewDTO {
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

    private User serviceProvider;
    private List<MerchandisePhotoDTO> merchandisePhotos;
    private List<EventType> eventTypes;
    private AddressDTO address;
    private Category category;
}
