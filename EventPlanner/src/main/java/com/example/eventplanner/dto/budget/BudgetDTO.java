package com.example.eventplanner.dto.budget;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BudgetDTO {
    private int budgetId;
    private double maxAmount;
    private double spentAmount;
    private List<BudgetItemDTO> budgetItems;
}
