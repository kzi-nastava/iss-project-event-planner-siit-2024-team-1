package com.example.eventplanner.model.event;

import com.example.eventplanner.model.merchandise.Merchandise;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double maxAmount;
    private double amountSpent;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Merchandise merchandise;
}
