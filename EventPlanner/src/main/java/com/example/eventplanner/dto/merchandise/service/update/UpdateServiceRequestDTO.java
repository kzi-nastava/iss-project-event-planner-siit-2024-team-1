package com.example.eventplanner.dto.merchandise.service.update;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.merchandise.MerchandisePhotoDTO;
import lombok.Data;

import java.util.List;
@Data
public class UpdateServiceRequestDTO {
    private String title;
    private String description;
    private String specificity;
    private double price;
    private int discount;
    private List<Integer> eventTypesIds;
    private int minDuration;
    private int maxDuration;
    private int reservationDeadline;
    private int cancellationDeadline;
    private boolean automaticReservation;
    private boolean visible;
    private boolean available;
    private List<MerchandisePhotoDTO> photos;
    private int serviceProviderId;
    private AddressDTO address;
}
