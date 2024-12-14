package com.example.eventplanner.services.category;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.category.CategoryRequestDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.services.merchandise.MerchandiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MerchandiseService merchandiseService;
    private final MerchandiseRepository merchandiseRepository;

    public List<CategoryOverviewDTO> getAllApprovedCategories() {
        List<Category> allCategories = categoryRepository.findAllApprovedCategories();
        return allCategories.stream().map(this::mapToCategoryOverviewDTO).toList();
    }

    private CategoryOverviewDTO mapToCategoryOverviewDTO(Category category) {
        CategoryOverviewDTO categoryOverviewDTO = new CategoryOverviewDTO();
        categoryOverviewDTO.setId(category.getId());
        categoryOverviewDTO.setTitle(category.getTitle());
        categoryOverviewDTO.setDescription(category.getDescription());
        categoryOverviewDTO.setPending(category.isPending());
        return categoryOverviewDTO;
    }

    public List<CategoryOverviewDTO> getAllPendingCategories() {
        List<Category> allCategories = categoryRepository.findAllPendingCategories();
        return allCategories.stream().map(this::mapToCategoryOverviewDTO).toList();
    }

    public List<CategoryOverviewDTO> createCategory(CategoryRequestDTO request) {
        Category category = new Category();
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
        category.setPending(request.isPending());
        categoryRepository.save(category);
        return getAllApprovedCategories();
    }

    public List<CategoryOverviewDTO> approveCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id: " + categoryId + " not found"));
        category.setPending(false);
        categoryRepository.save(category);
        return getAllApprovedCategories();
    }

    public List<CategoryOverviewDTO> updateCategory(int categoryId, CategoryRequestDTO request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id: " + categoryId + " not found"));
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
        category.setPending(false);
        categoryRepository.save(category);
        return getAllApprovedCategories();
    }

    public List<CategoryOverviewDTO> deleteCategory(int categoryId) throws Exception {
            boolean exists = categoryRepository.existsById(categoryId);
            if(!exists) {
                throw new IllegalArgumentException("Category with id: " + categoryId + " not found");
            }

            List<Merchandise> merchandise = merchandiseRepository.findMerchandiseByCategory(categoryId);
            if(merchandise.isEmpty()) {
                categoryRepository.deleteById(categoryId);
            }else {
                throw new Exception("Cannot delete category, it is connected with merchandise");
            }

        return getAllApprovedCategories();
    }
}
