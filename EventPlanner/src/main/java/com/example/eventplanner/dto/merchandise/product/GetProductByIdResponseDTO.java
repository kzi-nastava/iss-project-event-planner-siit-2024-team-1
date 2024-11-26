package com.example.eventplanner.dto.merchandise.product;

import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import com.example.eventplanner.model.user.ServiceProvider;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

import java.util.List;

@Data
public class GetProductByIdResponseDTO {
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
    private ServiceProvider serviceProvider;
    private List<MerchandisePhoto> photos;
    private List<EventType> eventTypes;
    @ManyToOne
    private Address address;
    @ManyToOne
    private Category category;
}
