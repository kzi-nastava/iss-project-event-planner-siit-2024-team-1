package com.example.eventplanner.dto.merchandise.product;

import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class MerchandiseOverviewDTO {
    private String category;
    private String type;
    private byte[] photo;
    private String title;
    private double rating;
    private Address address;
    private String description;
}
