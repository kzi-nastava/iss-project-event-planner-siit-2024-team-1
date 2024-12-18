package com.example.eventplanner.dto.merchandise.product.update;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.merchandise.CreateMerchandisePhotoDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.user.ServiceProvider;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class UpdateProductRequestDTO {
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

    private int serviceProviderId;
    private List<Integer> merchandisePhotos;
    private List<Integer> eventTypesIds;
    private AddressDTO address;
}
