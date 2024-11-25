package com.example.eventplanner.dto.merchandise.service.create;

import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import com.example.eventplanner.model.merchandise.Timeslot;
import jakarta.persistence.ManyToOne;
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
    private boolean deleted;
    private List<Timeslot> timeslot;
    private List<MerchandisePhoto> photos;
    private List<EventType> eventTypes;
    @ManyToOne
    private Address address;
    @ManyToOne
    private Category category;
}
