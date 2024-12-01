package com.example.eventplanner.services.event;

import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.repositories.event.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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
