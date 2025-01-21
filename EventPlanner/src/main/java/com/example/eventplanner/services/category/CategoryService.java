package com.example.eventplanner.services.category;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.category.CategoryRequestDTO;
import com.example.eventplanner.exceptions.CategoryException;
import com.example.eventplanner.model.common.NotificationType;
import com.example.eventplanner.model.event.BudgetItem;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.MerchandiseState;
import com.example.eventplanner.model.merchandise.Product;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.repositories.budget.BudgetItemRepository;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.eventType.EventTypeRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final EventTypeRepository eventTypeRepository;
    private final BudgetItemRepository budgetItemRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final NotificationService notificationService;

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

    public void replaceCategory(int categoryId, int replacedCategoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryException("Category with id: " + categoryId + " not found", CategoryException.ErrorType.CATEGORY_NOT_FOUND));
        boolean categoryFound = categoryRepository.existsById(replacedCategoryId);
        if(!categoryFound) {
            throw new CategoryException("Category with id: " + replacedCategoryId + " not found", CategoryException.ErrorType.CATEGORY_NOT_FOUND);
        }
        List<Merchandise> merchandises = merchandiseRepository.findMerchandiseByCategory(replacedCategoryId);
        if(!merchandises.isEmpty()) {
            for(Merchandise merc : merchandises) {
                merc.setCategory(category);
                merchandiseRepository.save(merc);
                notifyServiceProvider(merc, "Admin has updated Category from one of your Merchandises");
            }
        } else {
            throw new CategoryException("No merchandise associated with category: " + replacedCategoryId, CategoryException.ErrorType.MERCHANDISE_NOT_FOUND);
        }
    }

    private void notifyServiceProvider(Merchandise merc, String message) {
        Optional<ServiceProvider> optionalServiceProvider = serviceProviderRepository.findByMerchandiseId(merc.getId());
        if(optionalServiceProvider.isPresent()) {
            ServiceProvider serviceProvider = optionalServiceProvider.get();
            if(merc instanceof Product) {
                notificationService.sendNotificationToUser(serviceProvider, message, NotificationType.PRODUCT, merc.getId());
            }else {
                notificationService.sendNotificationToUser(serviceProvider, message, NotificationType.SERVICE, merc.getId());
            }
        }
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
                notifyServiceProvider(merc, "Admin has updated Category from one of your Merchandises");
            }
        }

        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryOverviewDTO(savedCategory);
    }

    public void deleteCategory(int categoryId) throws Exception {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new CategoryException("Category with id: " + categoryId + " not found", CategoryException.ErrorType.CATEGORY_NOT_FOUND)
                );
        List<EventType> eventTypes = eventTypeRepository.findEventTypesByCategoryId(categoryId);
        if(!eventTypes.isEmpty()) {
            for(EventType eventType : eventTypes) {
                eventType.getCategories().remove(category);
                eventTypeRepository.save(eventType);
            }
        }
        List<BudgetItem> budgetItems = budgetItemRepository.findByCategoryId(categoryId);
        List<Merchandise> merchandise = merchandiseRepository.findMerchandiseByCategory(categoryId);
        if(merchandise.isEmpty() && budgetItems.isEmpty()) {
            categoryRepository.deleteById(categoryId);
        }else {
            throw new CategoryException("Category with id: " + categoryId + " is already in use", CategoryException.ErrorType.CATEGORY_IN_USE);
        }
    }
}
