package com.example.eventplanner.dto.budget;

import com.example.eventplanner.dto.common.AddressDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MerchandiseBudgetDTO {
    private int id;
    private String title;
    private String description;
    private BudgetItemCategoryDTO category;
    private double rating;
    private AddressDTO address;
    private double price;
}
