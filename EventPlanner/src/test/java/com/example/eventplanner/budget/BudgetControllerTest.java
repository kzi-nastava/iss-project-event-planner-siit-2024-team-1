package com.example.eventplanner.budget;

import com.example.eventplanner.config.TestSecurityConfig;
import com.example.eventplanner.dto.budget.BudgetDTO;
import com.example.eventplanner.dto.budget.CreateBudgetRequestDTO;
import com.example.eventplanner.dto.budget.UpdateBudgetRequestDTO;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Budget;
import com.example.eventplanner.model.event.BudgetItem;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.repositories.budget.BudgetItemRepository;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("jpatest")
@Import(TestSecurityConfig.class)
public class BudgetControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BudgetItemRepository budgetItemRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private MerchandiseRepository merchandiseRepository;

    private Event event;
    private Category category;
    private Merchandise merchandise;
    private Budget budget;
    private BudgetItem budgetItem;
    private BudgetItem budgetItemWithMerchandise;
    private CreateBudgetRequestDTO validCreateBudgetRequestDTO;
    private UpdateBudgetRequestDTO validUpdateBudgetRequestDTO;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setTitle("Test Category");
        category.setDescription("Test Category Description");
        category.setPending(false);
        category = categoryRepository.save(category);

        budgetItem = new BudgetItem();
        budgetItem.setCategory(category);
        budgetItem.setMaxAmount(5000);
        budgetItem.setAmountSpent(0);
        budgetItem = budgetItemRepository.save(budgetItem);

        merchandise = new Merchandise();
        merchandise.setTitle("Merchandise Title");
        merchandise.setDescription("Merchandise Description");
        merchandise.setCategory(category);
        merchandise.setAddress(new Address());
        merchandise = merchandiseRepository.save(merchandise);

        budgetItemWithMerchandise = new BudgetItem();
        budgetItemWithMerchandise.setCategory(category);
        budgetItemWithMerchandise.setMaxAmount(5000);
        budgetItemWithMerchandise.setMerchandise(merchandise);
        budgetItemWithMerchandise = budgetItemRepository.save(budgetItemWithMerchandise);

        budget = new Budget();
        budget.setBudgetItems(new ArrayList<>());
        budget.getBudgetItems().add(budgetItem);
        budget.getBudgetItems().add(budgetItemWithMerchandise);

        event = new Event();
        event.setAddress(new Address());
        event.setBudget(budget);
        event = eventRepository.save(event);

        validCreateBudgetRequestDTO = new CreateBudgetRequestDTO();
        validCreateBudgetRequestDTO.setMaxAmount(5000);
        validCreateBudgetRequestDTO.setCategoryId(category.getId());

        validUpdateBudgetRequestDTO = new UpdateBudgetRequestDTO();
        validUpdateBudgetRequestDTO.setBudgetId(budget.getBudgetId());
        validUpdateBudgetRequestDTO.setPrice(6000);
    }

    @Test
    void getBudgetByEvent_ValidId_ReturnsOk() {
        ResponseEntity<BudgetDTO> response = restTemplate.getForEntity(
                "/api/v1/budget/event/{eventId}",
                BudgetDTO.class,
                event.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBudgetId()).isEqualTo(budget.getBudgetId());
    }

    @Test
    void getBudgetByEvent_InvalidId_ReturnsNotFound() {
        ResponseEntity<BudgetDTO> response = restTemplate.getForEntity(
                "/api/v1/budget/event/{eventId}",
                BudgetDTO.class,
                999
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void createBudgetItem_ValidRequest_ReturnsBudget() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateBudgetRequestDTO> request = new HttpEntity<>(validCreateBudgetRequestDTO, headers);
        ResponseEntity<BudgetDTO> response = restTemplate.postForEntity(
                "/api/v1/budget/create/{budgetId}",
                request,
                BudgetDTO.class,
                budget.getBudgetId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBudgetItems()).isNotNull().anySatisfy(budgetItemDTO -> {
            assertThat(budgetItemDTO.getMaxAmount()).isEqualTo(validCreateBudgetRequestDTO.getMaxAmount());
            assertThat(budgetItemDTO.getCategory().getId()).isEqualTo(validCreateBudgetRequestDTO.getCategoryId());
        });
    }

    @Test
    void createBudgetItem_InvalidBudgetId_ReturnsNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateBudgetRequestDTO> request = new HttpEntity<>(validCreateBudgetRequestDTO, headers);
        ResponseEntity<BudgetDTO> response = restTemplate.postForEntity(
                "/api/v1/budget/create/{budgetId}",
                request,
                BudgetDTO.class,
                999
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void createBudgetItem_InvalidCategory_ReturnsNotFound() {
        CreateBudgetRequestDTO invalidCategoryRequest = new CreateBudgetRequestDTO();
        invalidCategoryRequest.setMaxAmount(5000);
        invalidCategoryRequest.setCategoryId(999);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CreateBudgetRequestDTO> request = new HttpEntity<>(invalidCategoryRequest, headers);

        ResponseEntity<BudgetDTO> response = restTemplate.postForEntity(
                "/api/v1/budget/create/{budgetId}",
                request,
                BudgetDTO.class,
                budget.getBudgetId()
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateBudgetItem_ValidRequest_ReturnsBudget() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateBudgetRequestDTO> request = new HttpEntity<>(validUpdateBudgetRequestDTO, headers);
        ResponseEntity<BudgetDTO> response = restTemplate.exchange(
                "/api/v1/budget/update/{budgetItemId}",
                HttpMethod.PUT,
                request,
                BudgetDTO.class,
                budgetItem.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getBudgetItems()).isNotNull().anySatisfy(budgetItemDTO -> {
            assertThat(budgetItemDTO.getMaxAmount()).isEqualTo(validUpdateBudgetRequestDTO.getPrice());
        });
    }

    @Test
    void updateBudgetItem_InvalidBudgetItemId_ReturnsNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UpdateBudgetRequestDTO> request = new HttpEntity<>(validUpdateBudgetRequestDTO, headers);
        ResponseEntity<BudgetDTO> response = restTemplate.exchange(
                "/api/v1/budget/update/{budgetItemId}",
                HttpMethod.PUT,
                request,
                BudgetDTO.class,
                999
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateBudgetItem_InvalidBudgetId_ReturnsNotFound() {
        UpdateBudgetRequestDTO request = new UpdateBudgetRequestDTO();
        request.setBudgetId(999);
        request.setPrice(6000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateBudgetRequestDTO> request1 = new HttpEntity<>(request, headers);

        ResponseEntity<BudgetDTO> response = restTemplate.exchange(
                "/api/v1/budget/update/{budgetItemId}",
                HttpMethod.PUT,
                request1,
                BudgetDTO.class,
                budgetItem.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateBudgetItem_MerchandiseExists_ReturnsConflict() {
        //TODO
        UpdateBudgetRequestDTO updateDTO = new UpdateBudgetRequestDTO();
        updateDTO.setBudgetId(budget.getBudgetId());
        updateDTO.setPrice(6000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UpdateBudgetRequestDTO> request = new HttpEntity<>(updateDTO, headers);

        ResponseEntity<BudgetDTO> response = restTemplate.exchange(
                "/api/v1/budget/update/{budgetItemId}",
                HttpMethod.PUT,
                request,
                BudgetDTO.class,
                budgetItemWithMerchandise.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void deleteBudgetItem_ValidRequest_ReturnsOk() {
        ResponseEntity<BudgetDTO> response = restTemplate.exchange(
                "/api/v1/budget/delete/{budgetId}/{budgetItemId}",
                HttpMethod.PUT,
                null,
                BudgetDTO.class,
                budget.getBudgetId(),
                budgetItem.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteBudgetItem_InvalidBudgetId_ReturnsNotFound() {
        ResponseEntity<BudgetDTO> response = restTemplate.exchange(
                "/api/v1/budget/delete/{budgetId}/{budgetItemId}",
                HttpMethod.PUT,
                null,
                BudgetDTO.class,
                999,
                budgetItem.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteBudgetItem_MerchandiseExists_ReturnsConflict() {
        ResponseEntity<BudgetDTO> response = restTemplate.exchange(
                "/api/v1/budget/delete/{budgetId}/{budgetItemId}",
                HttpMethod.PUT,
                null,
                BudgetDTO.class,
                budget.getBudgetId(),
                budgetItemWithMerchandise.getId()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void deleteBudgetItem_InvalidBudgetItemId_ReturnsNotFound() {
        ResponseEntity<BudgetDTO> response = restTemplate.exchange(
                "/api/v1/budget/delete/{budgetId}/{budgetItemId}",
                HttpMethod.PUT,
                null,
                BudgetDTO.class,
                budget.getBudgetId(),
                999
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
