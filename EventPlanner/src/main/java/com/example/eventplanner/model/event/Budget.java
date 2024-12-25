package com.example.eventplanner.model.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int budgetId;

    @OneToMany
    @JoinTable(
            inverseJoinColumns = @JoinColumn(name = "budget_item_id")
    )
    private List<BudgetItem> budgetItems;


    @Transient
    private double maxAmount;

    public double getMaxAmount() {
        if(this.budgetItems != null) {
            return this.getBudgetItems().stream()
                    .mapToDouble(BudgetItem::getMaxAmount)
                    .sum();
        }
        return 0.0;
    }

    @Transient
    private double spentAmount;

    public double getSpentAmount() {
        if(this.getBudgetItems() != null) {
            return this.getBudgetItems().stream()
                    .mapToDouble(BudgetItem::getAmountSpent)
                    .sum();
        }
        return 0.0;
    }
}
