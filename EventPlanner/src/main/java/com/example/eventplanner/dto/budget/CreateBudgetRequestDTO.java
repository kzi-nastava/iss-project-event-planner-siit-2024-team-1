package com.example.eventplanner.dto.budget;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateBudgetRequestDTO {
    private int categoryId;
    private double maxAmount;
}
