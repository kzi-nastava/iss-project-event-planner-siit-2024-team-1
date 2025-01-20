package com.example.eventplanner.services.category;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.category.CategoryRequestDTO;
import com.example.eventplanner.exceptions.CategoryException;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.MerchandiseState;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.eventType.EventTypeRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
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
    private final ServiceProviderRepository serviceProviderRepository;
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;

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

    public CategoryOverviewDTO createCategory(CategoryRequestDTO request) {
        Category category = new Category();
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
        category.setPending(request.isPending());
        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryOverviewDTO(savedCategory);
    }

    public CategoryOverviewDTO approveCategory(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryException("Category with id: " + categoryId + " not found", CategoryException.ErrorType.CATEGORY_NOT_FOUND));
        category.setPending(false);
        //event type with id 1 is option 'all' and it has all categories connected to it
        EventType eventType = eventTypeRepository.findById(1).orElseThrow(() ->
                new CategoryException("Event type with id: " + 1 + " not found", CategoryException.ErrorType.EVENT_NOT_FOUND));
        List<Category> allCategories = eventType.getCategories();
        allCategories.add(category);
        eventType.setCategories(allCategories);
        eventTypeRepository.save(eventType);

        List<Merchandise> merchandise = merchandiseRepository.findMerchandiseByCategory(categoryId);
        if(!merchandise.isEmpty()) {
            for(Merchandise merc : merchandise){
                if(merc.getState() == MerchandiseState.PENDING) {
                    merc.setState(MerchandiseState.APPROVED);
                    merchandiseRepository.save(merc);
                }
            }
        }

        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryOverviewDTO(savedCategory);
    }

    public CategoryOverviewDTO updateCategory(int categoryId, CategoryRequestDTO request) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryException("Category with id: " + categoryId + " not found", CategoryException.ErrorType.CATEGORY_NOT_FOUND));
        category.setTitle(request.getTitle());
        category.setDescription(request.getDescription());
        category.setPending(false);

        List<Merchandise> merchandise = merchandiseRepository.findMerchandiseByCategory(categoryId);
        if(!merchandise.isEmpty()) {
            for(Merchandise merc : merchandise){
                if(merc.getState() == MerchandiseState.PENDING) {
                    merc.setState(MerchandiseState.APPROVED);
                    merchandiseRepository.save(merc);
                }
            }
        }

        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryOverviewDTO(savedCategory);
    }

    public void deleteCategory(int categoryId) throws Exception {
            boolean exists = categoryRepository.existsById(categoryId);
            if(!exists) {
                throw new CategoryException("Category with id: " + categoryId + " not found", CategoryException.ErrorType.CATEGORY_NOT_FOUND);
            }

        List<Merchandise> merchandise = merchandiseRepository.findMerchandiseByCategory(categoryId);
        if(merchandise.isEmpty()) {
            categoryRepository.deleteById(categoryId);
        }else {
            for(Merchandise merc : merchandise){
                if(merc.getState() == MerchandiseState.PENDING) {
                    ServiceProvider sp = serviceProviderRepository.findByMerchandiseId(merc.getId()).get();
                    sp.getMerchandise().remove(merc);
                    serviceProviderRepository.save(sp);
                    merchandiseRepository.deleteById(merc.getId());
                }
            }
        }
    }
}
