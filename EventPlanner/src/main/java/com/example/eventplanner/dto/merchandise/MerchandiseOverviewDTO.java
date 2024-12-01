package com.example.eventplanner.dto.merchandise;

import com.example.eventplanner.model.common.Address;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class MerchandiseOverviewDTO {
    private int id;
    private String category;
    private String type;
    private byte[] photo;
    private String title;
    private double rating;
    private Address address;
    private double price;
    private String description;
}
