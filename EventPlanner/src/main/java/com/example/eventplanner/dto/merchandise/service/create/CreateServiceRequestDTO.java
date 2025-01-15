package com.example.eventplanner.dto.merchandise.service.create;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.merchandise.CreateMerchandisePhotoDTO;
import com.example.eventplanner.dto.merchandise.product.CreateCategoryDTO;
import lombok.Data;

import java.util.List;

@Data
public class CreateServiceRequestDTO {
    private String title;
    private String description;
    private String specificity;
    private double price;
    private int discount;
    private int categoryId;
    private CreateCategoryDTO category;
    private List<Integer> eventTypesIds;
    private int minDuration;
    private int maxDuration;
    private int reservationDeadline;
    private int cancellationDeadline;
    private boolean automaticReservation;
    private List<Integer> merchandisePhotos;
    private int serviceProviderId;
    private AddressDTO address;

}
