package com.example.eventplanner.repositories.budget;

import com.example.eventplanner.model.event.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Integer> {
    @Query("SELECT bi FROM BudgetItem bi WHERE bi.category.id = :categoryId")
    List<BudgetItem> findByCategoryId(@Param("categoryId") int categoryId);
}
