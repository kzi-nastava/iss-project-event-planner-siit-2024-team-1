package com.example.eventplanner.dto.merchandise;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.merchandise.review.DetailsReviewOverviewDTO;
import lombok.Data;

import java.util.List;

@Data
public class MerchandiseDetailDTO {
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
    private List<MerchandisePhotoDTO> merchandisePhotos;
    private List<DetailsReviewOverviewDTO> reviews;
    private AddressDTO address;
    private CategoryOverviewDTO category;
    private List<EventTypeOverviewDTO> eventTypes;
    private double rating;
    private int serviceProviderId;
    private String type;
}
