package com.example.eventplanner.budget;

import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.BudgetItem;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.repositories.budget.BudgetItemRepository;
import com.example.eventplanner.repositories.budget.BudgetRepository;
import com.example.eventplanner.repositories.category.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("jpatest")
public class BudgetRepositoryTest {
    @Autowired
    private BudgetItemRepository budgetItemRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Category category1;
    private Category category2;
    private BudgetItem budgetItem1;
    private BudgetItem budgetItem2;
    @Autowired
    private BudgetRepository budgetRepository;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setTitle("Category Title");
        category1.setDescription("Category Description");
        category1.setPending(false);
        category1 = categoryRepository.save(category1);

        category2 = new Category();
        category2.setTitle("Category Title2");
        category2.setDescription("Category Description2");
        category2.setPending(false);
        category2 = categoryRepository.save(category2);

        budgetItem1 = new BudgetItem();
        budgetItem1.setMaxAmount(5000);
        budgetItem1.setAmountSpent(0);
        budgetItem1.setCategory(category1);

        budgetItem2 = new BudgetItem();
        budgetItem2.setMaxAmount(6000);
        budgetItem2.setAmountSpent(0);
        budgetItem2.setCategory(category2);
    }

    @Test
    void findByCategoryId_ValidCategoryId_ReturnsCorrectBudgetItem() {
        BudgetItem savedBudgetItem1 = budgetItemRepository.save(budgetItem1);
        BudgetItem savedBudgetItem2 = budgetItemRepository.save(budgetItem2);

        List<BudgetItem> foundBudgetItem1 = budgetItemRepository.findByCategoryId(category1.getId());
        assertEquals(1, foundBudgetItem1.size());
        assertEquals(savedBudgetItem1.getId(), foundBudgetItem1.get(0).getId());

        List<BudgetItem> foundBudgetItem2 = budgetItemRepository.findByCategoryId(category2.getId());
        assertEquals(1, foundBudgetItem2.size());
        assertEquals(savedBudgetItem2.getId(), foundBudgetItem2.get(0).getId());
    }

    @Test
    void findByCategoryId_NonExistentCategoryId_ReturnsEmptyList() {
        budgetItemRepository.save(budgetItem1);

        Category nonExistentCategory = new Category();
        nonExistentCategory.setTitle("NonExistent Category Title");
        nonExistentCategory.setDescription("NonExistent Category Description");
        nonExistentCategory.setPending(false);
        nonExistentCategory = categoryRepository.save(nonExistentCategory);

        List<BudgetItem> found = budgetItemRepository.findByCategoryId(nonExistentCategory.getId());
        assertTrue(found.isEmpty());
    }
}
