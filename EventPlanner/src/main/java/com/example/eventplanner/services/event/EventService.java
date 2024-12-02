package com.example.eventplanner.services.event;

import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.filter.EventFiltersDTO;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.repositories.event.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Page<EventOverviewDTO> getTop(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::convertToOverviewDTO);
    }

    public Page<EventOverviewDTO> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::convertToOverviewDTO);
    }

    public Page<EventOverviewDTO> search(EventFiltersDTO eventFiltersDTO, String search, Pageable pageable) {
        Specification<Event> spec = createSpecification(eventFiltersDTO, search);
        Page<Event> events = eventRepository.findAll(spec, pageable);
        return events.map(this::convertToOverviewDTO);
    }

    private Specification<Event> createSpecification(EventFiltersDTO eventFiltersDTO, String search) {
        Specification<Event> spec = Specification.where(null);
        spec = addDateRangeFilter(spec, eventFiltersDTO);
        spec = addTypeFilter(spec, eventFiltersDTO);
        spec = addCityFilter(spec, eventFiltersDTO);
        spec = addGlobalSearch(spec, search);
        return spec;
    }

    private Specification<Event> addDateRangeFilter(Specification<Event> spec, EventFiltersDTO eventFiltersDTO) {
        if (eventFiltersDTO.getStartDate() != null && eventFiltersDTO.getEndDate() != null) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("date"),
                            eventFiltersDTO.getStartDate(),
                            eventFiltersDTO.getEndDate())
            );
        }
        return spec;
    }

    private Specification<Event> addTypeFilter(Specification<Event> spec, EventFiltersDTO eventFiltersDTO) {
        if (StringUtils.hasText(eventFiltersDTO.getType())) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("type").get("title"),
                            eventFiltersDTO.getType()
                    )
            );
        }
        return spec;
    }

    private Specification<Event> addCityFilter(Specification<Event> spec, EventFiltersDTO eventFiltersDTO) {
        if (StringUtils.hasText(eventFiltersDTO.getCity())) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("address").get("city"),
                            eventFiltersDTO.getCity()
                    )
            );
        }
        return spec;
    }

    private Specification<Event> addGlobalSearch(Specification<Event> spec, String search) {
        if (StringUtils.hasText(search)) {
            return spec.and((root, query, criteriaBuilder) -> {
                String searchPattern = "%" + search.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("address").get("city")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("type").get("title")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("address").get("street")), searchPattern)
                );
            });
        }
        return spec;
    }

    private EventOverviewDTO convertToOverviewDTO(Event event) {
        EventOverviewDTO dto = new EventOverviewDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setDate(event.getDate());
        dto.setAddress(event.getAddress());
        dto.setType(event.getType() != null ? event.getType().getTitle() : null);
        return dto;
    }
}
