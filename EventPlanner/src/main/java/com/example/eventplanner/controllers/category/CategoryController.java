package com.example.eventplanner.controllers.category;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.category.CategoryRequestDTO;
import com.example.eventplanner.services.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/get/approved")
    public ResponseEntity<List<CategoryOverviewDTO>> getAllApprovedCategories() {
        List<CategoryOverviewDTO> approvedCategories = categoryService.getAllApprovedCategories();
        return ResponseEntity.ok(approvedCategories);
    }

    @GetMapping("/get/pending")
    public ResponseEntity<List<CategoryOverviewDTO>> getAllPendingCategories() {
        List<CategoryOverviewDTO> pendingCategories = categoryService.getAllPendingCategories();
        return ResponseEntity.ok(pendingCategories);
    }

    @PostMapping("/create")
    public ResponseEntity<List<CategoryOverviewDTO>> createCategory(@RequestBody CategoryRequestDTO request) {
        List<CategoryOverviewDTO> approvedCategories = categoryService.createCategory(request);
        return ResponseEntity.ok(approvedCategories);
    }

    @PutMapping("/approve/{categoryId}")
    public ResponseEntity<List<CategoryOverviewDTO>> approveCategory(@PathVariable (value = "categoryId") int categoryId) {
        List<CategoryOverviewDTO> approvedCategories = categoryService.approveCategory(categoryId);
        return ResponseEntity.ok(approvedCategories);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<List<CategoryOverviewDTO>> updateCategory(@PathVariable (value = "categoryId") int categoryId,
                                                                    @RequestBody CategoryRequestDTO request) {
        List<CategoryOverviewDTO> approvedCategories = categoryService.updateCategory(categoryId, request);
        return ResponseEntity.ok(approvedCategories);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable (value = "categoryId") int categoryId) {
        try {
            List<CategoryOverviewDTO> approvedCategories = categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(approvedCategories);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
