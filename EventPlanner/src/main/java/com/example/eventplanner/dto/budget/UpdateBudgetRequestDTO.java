package com.example.eventplanner.dto.budget;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateBudgetRequestDTO {
    private int budgetId;
    private double price;
}
