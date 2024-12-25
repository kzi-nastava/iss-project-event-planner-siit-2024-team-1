package com.example.eventplanner.repositories.budget;

import com.example.eventplanner.model.event.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Integer> {
}
