package com.example.eventplanner.dto.merchandise.product;

import lombok.Data;

@Data
public class BuyProductResponseDTO {
    private double updatedBudget;
    private String category;
    private boolean success;
    private String message;
}
