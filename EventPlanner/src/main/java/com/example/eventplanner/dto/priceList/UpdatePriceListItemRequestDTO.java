package com.example.eventplanner.dto.priceList;

import lombok.Data;

@Data
public class UpdatePriceListItemRequestDTO {
    private double price;
    private int discount;
}
