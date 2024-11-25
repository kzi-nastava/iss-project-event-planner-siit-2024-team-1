package com.example.eventplanner.dto.priceList;

import lombok.Data;

@Data
public class UpdatePriceListItemRequestDTO {
    private int merchandiseId;
    private double price;
    private double discount;
}
