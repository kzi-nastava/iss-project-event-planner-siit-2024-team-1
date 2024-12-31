package com.example.eventplanner.dto.priceList;

import lombok.Data;

@Data
public class PriceListItemResponseDTO {
    private int merchandiseId;
    private String title;
    private double price;
    private int discount;
    private double discountedPrice;
}