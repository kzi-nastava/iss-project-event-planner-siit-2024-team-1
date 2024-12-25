package com.example.eventplanner.controllers.budget;

import com.example.eventplanner.dto.budget.BudgetDTO;
import com.example.eventplanner.dto.budget.CreateBudgetRequestDTO;
import com.example.eventplanner.dto.budget.UpdateBudgetRequestDTO;
import com.example.eventplanner.services.budget.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/budget")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @GetMapping("/event/{eventId}")
    public ResponseEntity<BudgetDTO> getBudgetByEvent(@PathVariable(name = "eventId") int eventId) {
        BudgetDTO budget = budgetService.getBudgetByEvent(eventId);
        return ResponseEntity.ok(budget);
    }

    @PostMapping("/create/{budgetId}")
    public ResponseEntity<BudgetDTO> createBudgetItem(@PathVariable(name = "budgetId") int budgetId,
                                                      @RequestBody CreateBudgetRequestDTO createBudgetRequestDTO) {
        BudgetDTO budgetDTO = budgetService.createBudgetItem(budgetId, createBudgetRequestDTO);
        return ResponseEntity.ok(budgetDTO);
    }

    @PutMapping("/update/{budgetItemId}")
    public ResponseEntity<BudgetDTO> updateBudgetItem(@PathVariable(name = "budgetItemId") int budgetItemId,
                                                      @RequestBody UpdateBudgetRequestDTO updateBudgetRequestDTO) {
        BudgetDTO budget = budgetService.updateBudgetItem(updateBudgetRequestDTO.getBudgetId(), budgetItemId, updateBudgetRequestDTO.getPrice());
        return ResponseEntity.ok(budget);
    }

    @PutMapping("/delete/{budgetId}/{budgetItemId}")
    public ResponseEntity<BudgetDTO> deleteBudgetItem(@PathVariable(name = "budgetId") int budgetId,
                                                      @PathVariable(name = "budgetItemId") int budgetItemId) {
        BudgetDTO budget = budgetService.deleteBudgetItem(budgetId, budgetItemId);
        return ResponseEntity.ok(budget);
    }
}
