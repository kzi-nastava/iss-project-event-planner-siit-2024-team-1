package com.example.eventplanner.dto.merchandise;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.merchandise.review.MerchandiseReviewOverviewDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import com.example.eventplanner.model.merchandise.Review;
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
    private List<MerchandiseReviewOverviewDTO> reviews;
    private AddressDTO address;
    private CategoryOverviewDTO category;
    private List<EventTypeOverviewDTO> eventTypes;
    private double rating;
}
