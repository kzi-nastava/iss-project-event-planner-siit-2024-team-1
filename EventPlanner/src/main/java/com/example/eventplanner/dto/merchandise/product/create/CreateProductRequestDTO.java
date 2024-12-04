package com.example.eventplanner.dto.merchandise.product.create;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.merchandise.CreateMerchandisePhotoDTO;
import com.example.eventplanner.dto.merchandise.product.CreateCategoryDTO;
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
public class CreateProductRequestDTO {
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
    private List<CreateMerchandisePhotoDTO> merchandisePhotos;
    private List<Integer> eventTypesIds;
    private AddressDTO address;
    private int categoryId;
    private CreateCategoryDTO category;
}
