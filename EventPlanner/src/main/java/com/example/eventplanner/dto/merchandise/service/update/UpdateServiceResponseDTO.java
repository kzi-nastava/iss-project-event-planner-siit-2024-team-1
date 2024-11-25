package com.example.eventplanner.dto.merchandise.service.update;

import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import com.example.eventplanner.model.merchandise.Review;
import com.example.eventplanner.model.merchandise.Timeslot;
import lombok.Data;

import java.util.List;
@Data
public class UpdateServiceResponseDTO {
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
    private boolean deleted;
    private List<Timeslot> timeslots;
    private List<MerchandisePhoto> photos;
    private List<EventType> eventTypes;
    private Address address;
    private Category category;
    private List<Review> reviews;

}
