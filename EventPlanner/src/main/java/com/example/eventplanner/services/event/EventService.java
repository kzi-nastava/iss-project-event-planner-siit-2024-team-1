package com.example.eventplanner.services.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.CreateEventDTO;
import com.example.eventplanner.dto.event.CreatedEventOverviewDTO;
import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetServiceByIdResponseDTO;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.Product;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.eventType.EventTypeRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final MerchandiseRepository merchandiseRepository;

    public Page<EventOverviewDTO> getTop(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::convertToOverviewDTO);
    }

    public Page<EventOverviewDTO> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::convertToOverviewDTO);
    }

    public CreatedEventOverviewDTO createEvent(CreateEventDTO dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setMaxParticipants(dto.getMaxParticipants());
        event.setPublic(dto.isPublic());
        event.setDate(dto.getDate());
        Address address = new Address();
        address.setCity(dto.getAddress().getCity());
        address.setStreet(dto.getAddress().getStreet());
        address.setNumber(dto.getAddress().getNumber());
        address.setLatitude(dto.getAddress().getLatitude());
        address.setLongitude(dto.getAddress().getLongitude());
        event.setAddress(address);

        List<EventType> eventTypes = eventTypeRepository.findAllById(dto.getEventTypeIds());
        if (!eventTypes.isEmpty()) {
            event.setType(eventTypes.get(0)); // Assuming the first type as primary
        }
        List<Merchandise> merchandise = merchandiseRepository.findAllById(dto.getProductIds());
        merchandise.addAll(merchandiseRepository.findAllById(dto.getServiceIds()));
        event.setMerchandise(merchandise);

        Event savedEvent = eventRepository.save(event);

        return mapToCreatedEventOverviewDTO(savedEvent, eventTypes, merchandise);
    }

    private CreatedEventOverviewDTO mapToCreatedEventOverviewDTO(Event event, List<EventType> eventTypes, List<Merchandise> merchandise) {
        CreatedEventOverviewDTO dto = new CreatedEventOverviewDTO();
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setPublic(event.isPublic());
        dto.setDate(event.getDate());

        // Map address
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(event.getAddress().getCity());
        addressDTO.setStreet(event.getAddress().getStreet());
        addressDTO.setNumber(event.getAddress().getNumber());
        addressDTO.setLatitude(event.getAddress().getLatitude());
        addressDTO.setLongitude(event.getAddress().getLongitude());
        dto.setAddress(addressDTO);

        // Map event types
        List<EventTypeOverviewDTO> eventTypeOverviewDTOs = eventTypes.stream()
                .map(eventType -> {
                    EventTypeOverviewDTO typeDTO = new EventTypeOverviewDTO();
                    typeDTO.setId(eventType.getId());
                    typeDTO.setTitle(eventType.getTitle());
                    typeDTO.setDescription(eventType.getDescription());
                    typeDTO.setActive(eventType.isActive());
                    return typeDTO;
                })
                .toList();
        dto.setEventTypes(eventTypeOverviewDTOs);

        // Separate and map merchandise into Products and Services
        List<GetProductByIdResponseDTO> productDTOs = merchandise.stream()
                .filter(merch -> merch instanceof Product)
                .map(merch -> {
                    Product product = (Product) merch;
                    GetProductByIdResponseDTO productDTO = new GetProductByIdResponseDTO();
                    productDTO.setId(product.getId());
                    productDTO.setTitle(product.getTitle());
                    productDTO.setDescription(product.getDescription());
                    return productDTO;
                })
                .toList();
        dto.setProducts(productDTOs);

        List<GetServiceByIdResponseDTO> serviceDTOs = merchandise.stream()
                .filter(merch -> merch instanceof com.example.eventplanner.model.merchandise.Service)
                .map(merch -> {
                    com.example.eventplanner.model.merchandise.Service service = (com.example.eventplanner.model.merchandise.Service) merch;
                    GetServiceByIdResponseDTO serviceDTO = new GetServiceByIdResponseDTO();
                    serviceDTO.setId(service.getId());
                    serviceDTO.setTitle(service.getTitle());
                    serviceDTO.setDescription(service.getDescription());
                    return serviceDTO;
                })
                .toList();
        dto.setServices(serviceDTOs);

        return dto;
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
