package com.example.eventplanner.dto.budget;

import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BudgetItemDTO {
    private int id;
    private BudgetItemCategoryDTO category;
    private double maxAmount;
    private double amountSpent;
    private MerchandiseBudgetDTO merchandise;
}
