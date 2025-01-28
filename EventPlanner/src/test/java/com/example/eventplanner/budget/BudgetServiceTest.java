package com.example.eventplanner.budget;

import com.example.eventplanner.dto.budget.BudgetDTO;
import com.example.eventplanner.dto.budget.CreateBudgetRequestDTO;
import com.example.eventplanner.exceptions.BudgetException;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.BudgetItem;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.repositories.budget.BudgetItemRepository;
import com.example.eventplanner.repositories.budget.BudgetRepository;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.services.budget.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceTest {

    @Mock
    private BudgetRepository budgetRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private BudgetItemRepository budgetItemRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private MerchandiseRepository merchandiseRepository;

    @InjectMocks
    private BudgetService budgetService;

    private Event event;
    private Budget budget;
    private BudgetItem budgetItem;
    private BudgetItem budgetItemWithMerchandise;
    private Category category1;
    private Category category2;
    private Merchandise merchandise;
    private CreateBudgetRequestDTO validCreateRequest;

    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1);
        category1.setTitle("Category 1 Title");
        category1.setDescription("Category 1 Description");
        category1.setPending(false);
        lenient().when(categoryRepository.findById(1)).thenReturn(Optional.of(category1));

        category2 = new Category();
        category2.setId(2);
        category2.setTitle("Category 2 Title");
        category2.setDescription("Category 2 Description");
        category2.setPending(false);
        lenient().when(categoryRepository.findById(2)).thenReturn(Optional.of(category2));

        merchandise = new Merchandise();
        merchandise.setId(1);
        merchandise.setTitle("Merchandise Title");
        merchandise.setCategory(category1);
        merchandise.setPrice(2000);
        merchandise.setDiscount(20);
        merchandise.setAddress(new Address());
        lenient().when(merchandiseRepository.findById(1)).thenReturn(Optional.of(merchandise));

        double discountedPrice = merchandise.getPrice() - (merchandise.getPrice() * merchandise.getDiscount())/100;

        budgetItem = new BudgetItem();
        budgetItem.setId(1);
        budgetItem.setCategory(category1);
        budgetItem.setMaxAmount(5000);
        budgetItem.setAmountSpent(0);
        lenient().when(budgetItemRepository.findById(1)).thenReturn(Optional.of(budgetItem));

        budgetItemWithMerchandise = new BudgetItem();
        budgetItemWithMerchandise.setId(2);
        budgetItemWithMerchandise.setCategory(category1);
        budgetItemWithMerchandise.setMaxAmount(5000);
        budgetItemWithMerchandise.setAmountSpent(discountedPrice);
        budgetItemWithMerchandise.setMerchandise(merchandise);
        lenient().when(budgetItemRepository.findById(2)).thenReturn(Optional.of(budgetItemWithMerchandise));

        budget = new Budget();
        budget.setBudgetId(1);
        budget.setBudgetItems(new ArrayList<>());
        budget.getBudgetItems().add(budgetItemWithMerchandise);
        budget.getBudgetItems().add(budgetItem);
        lenient().when(budgetRepository.findById(1)).thenReturn(Optional.of(budget));

        event = new Event();
        event.setId(1);
        event.setTitle("Event Title");
        event.setBudget(budget);
        lenient().when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        validCreateRequest = new CreateBudgetRequestDTO();
        validCreateRequest.setCategoryId(category1.getId());
        validCreateRequest.setMaxAmount(5000);
    }

    @Test
    @DisplayName("getBudget-Success")
    @Tag("success")
    void getBudgetByEvent_ValidBudgetId_Success() {
        BudgetDTO response = budgetService.getBudgetByEvent(1);

        assertNotNull(response);
        assertEquals(budget.getBudgetId(), response.getBudgetId());
    }

    @Test
    @DisplayName("getBudget-InvalidBudgetId")
    @Tag("not-found")
    void getBudgetByEvent_InvalidBudgetId_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.getBudgetByEvent(999)
        );
        assertEquals(BudgetException.ErrorType.EVENT_NOT_FOUND, exception.getErrorType());
    }

    @Test
    @DisplayName("deleteBudgetItem-Success")
    @Tag("success")
    void deleteBudgetItem_ValidRequest_Success() {
        BudgetDTO response = budgetService.deleteBudgetItem(1, 1);

        assertNotNull(response);
        assertEquals(budget.getBudgetItems().size(), 1);
    }

    @Test
    @DisplayName("deleteBudgetItem-InvalidBudgetId")
    @Tag("not-found")
    void deleteBudgetItem_InvalidBudgetId_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.deleteBudgetItem(999, 1)
        );
        assertEquals(BudgetException.ErrorType.BUDGET_NOT_FOUND, exception.getErrorType());
    }

    @Test
    @DisplayName("deleteBudgetItem-InvalidBudgetItemId")
    @Tag("not-found")
    void deleteBudgetItem_InvalidBudgetItemId_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.deleteBudgetItem(1, 999)
        );
        assertEquals(BudgetException.ErrorType.BUDGET_ITEM_NOT_FOUND, exception.getErrorType());
    }

    @Test
    @DisplayName("deleteBudgetItem-MerchandiseExists")
    @Tag("exists")
    void deleteBudgetItem_MerchandiseExists_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.deleteBudgetItem(1, 2)
        );
        assertEquals(BudgetException.ErrorType.MERCHANDISE_EXISTS, exception.getErrorType());
    }

    @Test
    @DisplayName("updateBudgetItem-Success")
    @Tag("success")
    void updateBudgetItem_ValidRequest_Success() {
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);
        BudgetDTO response = budgetService.updateBudgetItem(1, 1, 7000);

        assertNotNull(response);
        assertEquals(7000, budget.getBudgetItems().get(1).getMaxAmount());
    }

    @Test
    @DisplayName("updateBudgetItem-InvalidBudgetId")
    @Tag("not-found")
    void updateBudgetItem_InvalidBudgetId_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.updateBudgetItem(999, 1, 1)
        );

        assertEquals(BudgetException.ErrorType.BUDGET_NOT_FOUND, exception.getErrorType());
    }

    @Test
    @DisplayName("updateBudgetItem-InvalidBudgetItemId")
    @Tag("not-found")
    void updateBudgetItem_InvalidBudgetItemId_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.updateBudgetItem(1, 999, 1)
        );

        assertEquals(BudgetException.ErrorType.BUDGET_ITEM_NOT_FOUND, exception.getErrorType());
    }

    @Test
    @DisplayName("updateBudgetItem-InvalidPrice")
    @Tag("illegal-value")
    void updateBudgetItem_InvalidRequest_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.updateBudgetItem(1, 1, -1)
        );

        assertEquals(BudgetException.ErrorType.PRICE_ILLEGAL_VALUE, exception.getErrorType());
    }

    @Test
    @DisplayName("updateBudgetItem-MerchandiseExists")
    @Tag("exists")
    void updateBudgetItem_MerchandiseExists_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.updateBudgetItem(1, 2, 7000)
        );

        assertEquals(BudgetException.ErrorType.MERCHANDISE_EXISTS, exception.getErrorType());
    }

    @Test
    @DisplayName("createBudgetItem-Success")
    @Tag("success")
    void createBudgetItem_ValidRequest_Success() {
        when(budgetItemRepository.save(any(BudgetItem.class))).thenReturn(budgetItem);
        when(budgetRepository.save(any(Budget.class))).thenReturn(budget);
        BudgetDTO response = budgetService.createBudgetItem(1, validCreateRequest);

        assertNotNull(response);
        assertEquals(response.getBudgetItems().size(), 3);
    }

    @Test
    @DisplayName("createBudgetItem-InvalidBudgetId")
    @Tag("not-found")
    void createBudgetItem_InvalidBudgetId_ThrowsException() {
        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.createBudgetItem(999, validCreateRequest)
        );

        assertEquals(BudgetException.ErrorType.BUDGET_NOT_FOUND, exception.getErrorType());
    }

    @Test
    @DisplayName("createBudgetItem-InvalidMaxAmount")
    @Tag("illegal-value")
    void createBudgetItem_InvalidMaxAmount_ThrowsException() {
        CreateBudgetRequestDTO request = new CreateBudgetRequestDTO();
        request.setMaxAmount(-1);
        request.setCategoryId(category2.getId());

        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.createBudgetItem(1, request)
        );

        assertEquals(BudgetException.ErrorType.PRICE_ILLEGAL_VALUE, exception.getErrorType());
    }

    @Test
    @DisplayName("createBudgetItem-InvalidCategoryId")
    @Tag("not-found")
    void createBudgetItem_InvalidCategoryId_ThrowsException() {
        CreateBudgetRequestDTO request = new CreateBudgetRequestDTO();
        request.setCategoryId(999);
        request.setMaxAmount(7000);

        BudgetException exception = assertThrows(
                BudgetException.class,
                () -> budgetService.createBudgetItem(1, request)
        );

        assertEquals(BudgetException.ErrorType.CATEGORY_NOT_FOUND, exception.getErrorType());
    }
}
