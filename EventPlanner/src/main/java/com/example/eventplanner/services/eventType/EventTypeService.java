package com.example.eventplanner.services.eventType;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.eventType.CreateEventTypeDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.eventType.UpdateEventTypeDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.eventType.EventTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private final CategoryRepository categoryRepository;

    public List<EventTypeOverviewDTO> getAll() {
        return eventTypeRepository.findAll()
                .stream()
                .map(this::convertToOverviewDTO)
                .toList();
    }

    public EventTypeOverviewDTO create(CreateEventTypeDTO dto) {
        EventType eventType = new EventType();
        eventType.setTitle(dto.getTitle());
        eventType.setDescription(dto.getDescription());
        eventType.setActive(dto.isActive());
        eventType.setCategories(categoryRepository.findAllById(dto.getRecommendedCategoryIds()));

        EventType savedEventType = eventTypeRepository.save(eventType);
        return convertToOverviewDTO(savedEventType);
    }

    public EventTypeOverviewDTO update(int id, UpdateEventTypeDTO dto) {
        EventType eventType = eventTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("EventType with id " + id + " not found"));

        eventType.setDescription(dto.getDescription());
        eventType.setActive(dto.isActive());
        eventType.setCategories(categoryRepository.findAllById(dto.getRecommendedCategoryIds()));

        EventType updatedEventType = eventTypeRepository.save(eventType);
        return convertToOverviewDTO(updatedEventType);
    }

    private EventTypeOverviewDTO convertToOverviewDTO(EventType eventType) {
        EventTypeOverviewDTO dto = new EventTypeOverviewDTO();
        dto.setId(eventType.getId());
        dto.setTitle(eventType.getTitle());
        dto.setDescription(eventType.getDescription());
        dto.setActive(eventType.isActive());
        dto.setRecommendedCategories(
                eventType.getCategories()
                        .stream()
                        .map(this::convertToCategoryDTO)
                        .toList());

        return dto;
    }
    private CategoryOverviewDTO convertToCategoryDTO(Category category) {
        CategoryOverviewDTO categoryDTO = new CategoryOverviewDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }
}
