package com.example.eventplanner.services.budget;

import com.example.eventplanner.dto.budget.*;
import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.BudgetItem;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.repositories.budget.BudgetItemRepository;
import com.example.eventplanner.repositories.budget.BudgetRepository;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final EventRepository eventRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private final BudgetItemRepository budgetItemRepository;

    public BudgetDTO getBudgetByEvent(int eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
            new RuntimeException("Cannot find event with id " + eventId)
        );

        return mapToBudgetDTO(event.getBudget());
    }

    private BudgetDTO mapToBudgetDTO(Budget budget) {
        BudgetDTO budgetDTO = new BudgetDTO();
        budgetDTO.setBudgetId(budget.getBudgetId());
        budgetDTO.setMaxAmount(budget.getMaxAmount());
        budgetDTO.setSpentAmount(budget.getSpentAmount());
        budgetDTO.setBudgetItems(budget.getBudgetItems().stream().map(this::mapToBudgetItemDTO).toList());
        return budgetDTO;
    }

    private BudgetItemDTO mapToBudgetItemDTO(BudgetItem budgetItem) {
        BudgetItemDTO budgetItemDTO = new BudgetItemDTO();
        budgetItemDTO.setId(budgetItem.getId());
        budgetItemDTO.setMaxAmount(budgetItem.getMaxAmount());
        budgetItemDTO.setAmountSpent(budgetItem.getAmountSpent());

        BudgetItemCategoryDTO categoryDTO = new BudgetItemCategoryDTO();
        categoryDTO.setId(budgetItem.getCategory().getId());
        categoryDTO.setTitle(budgetItem.getCategory().getTitle());
        budgetItemDTO.setCategory(categoryDTO);

        if(budgetItem.getMerchandise() != null) {
            MerchandiseBudgetDTO merchandiseDTO = new MerchandiseBudgetDTO();
            merchandiseDTO.setId(budgetItem.getMerchandise().getId());
            merchandiseDTO.setType(budgetItem.getMerchandise().getClass().getSimpleName());
            merchandiseDTO.setTitle(budgetItem.getMerchandise().getTitle());
            merchandiseDTO.setDescription(budgetItem.getMerchandise().getDescription());
            merchandiseDTO.setRating(budgetItem.getMerchandise().getRating());

            BudgetItemCategoryDTO merchandiseCategoryDTO = new BudgetItemCategoryDTO();
            merchandiseCategoryDTO.setId(budgetItem.getMerchandise().getCategory().getId());
            merchandiseCategoryDTO.setTitle(budgetItem.getMerchandise().getCategory().getTitle());
            merchandiseDTO.setCategory(merchandiseCategoryDTO);

            double discountedPrice = budgetItem.getMerchandise().getPrice() -
                    (budgetItem.getMerchandise().getPrice() * budgetItem.getMerchandise().getDiscount())/100;
            merchandiseDTO.setPrice(discountedPrice);

            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(budgetItem.getMerchandise().getAddress().getCity());
            addressDTO.setStreet(budgetItem.getMerchandise().getAddress().getStreet());
            addressDTO.setNumber(budgetItem.getMerchandise().getAddress().getNumber());
            addressDTO.setLongitude(budgetItem.getMerchandise().getAddress().getLongitude());
            addressDTO.setLatitude(budgetItem.getMerchandise().getAddress().getLatitude());
            merchandiseDTO.setAddress(addressDTO);

            budgetItemDTO.setMerchandise(merchandiseDTO);
        }else {
            budgetItemDTO.setMerchandise(null);
        }

        return budgetItemDTO;
    }

    public BudgetDTO deleteBudgetItem(int budgetId, int budgetItemId) {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow(() ->
                new RuntimeException("Cannot find budget with id " + budgetId));
        BudgetItem budgetItem = budget.getBudgetItems()
                .stream()
                .filter(budgetItem1 -> budgetItem1.getId() == budgetItemId)
                .findFirst()
                .orElse(null);
        if(budgetItem != null) {
            if(budgetItem.getMerchandise() != null) {
                throw new RuntimeException("Cannot delete budget item because it already has merchandise");
            }else {
                budget.getBudgetItems().remove(budgetItem);
                budgetRepository.save(budget);
            }
        }else {
            throw new RuntimeException("Cannot find budget item with id " + budgetItemId);
        }

        return mapToBudgetDTO(budget);
    }

    public BudgetDTO updateBudgetItem(int budgetId, int budgetItemId, double price) {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow(() ->
                new RuntimeException("Cannot find budget with id " + budgetId));
        BudgetItem budgetItem = budget.getBudgetItems()
                .stream()
                .filter(budgetItem1 -> budgetItem1.getId() == budgetItemId)
                .findFirst()
                .orElse(null);
        if(budgetItem != null) {
            if(budgetItem.getMerchandise() != null) {
                throw new RuntimeException("Cannot update budget item because it already has merchandise");
            }else {
                budgetItem.setMaxAmount(price);
                budgetRepository.save(budget);
            }
        }else {
            throw new RuntimeException("Cannot find budget item with id " + budgetItemId);
        }

        return mapToBudgetDTO(budget);
    }

    public BudgetDTO createBudgetItem(int budgetId, CreateBudgetRequestDTO createBudgetRequestDTO) {
        Budget budget = budgetRepository.findById(budgetId).orElseThrow(() ->
                new RuntimeException("Cannot find budget with id " + budgetId));
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setAmountSpent(0.0);
        budgetItem.setMaxAmount(createBudgetRequestDTO.getMaxAmount());
        Category category = categoryRepository.findById(createBudgetRequestDTO.getCategoryId()).orElse(null);
        if(category != null) {
            budgetItem.setCategory(category);
        }else {
            throw new RuntimeException("Cannot find category with id " + createBudgetRequestDTO.getCategoryId());
        }
        BudgetItem savedBudgetItem = budgetItemRepository.save(budgetItem);
        budget.getBudgetItems().add(savedBudgetItem);
        budgetRepository.save(budget);

        return mapToBudgetDTO(budget);
    }
}
