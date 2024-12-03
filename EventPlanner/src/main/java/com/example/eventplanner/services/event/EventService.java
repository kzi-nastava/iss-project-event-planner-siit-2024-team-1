package com.example.eventplanner.services.event;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.*;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.service.GetServiceByIdResponseDTO;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Activity;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.Product;
import com.example.eventplanner.repositories.event.ActivityRepository;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.eventType.EventTypeRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final ActivityRepository activityRepository;

    public Page<EventOverviewDTO> getTop(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::convertToOverviewDTO);
    }

    public Page<EventOverviewDTO> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable)
                .map(this::convertToOverviewDTO);
    }

    public EventDetailsDTO getDetails(int eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        EventDetailsDTO dto = new EventDetailsDTO();
        dto.setId(event.getId());
        dto.setType(event.getType() != null ? event.getType().getTitle() : null);
        dto.setTitle(event.getTitle());
        dto.setDate(event.getDate());

        Address address = event.getAddress();
        if (address != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(address.getCity());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setNumber(address.getNumber());
            addressDTO.setLatitude(address.getLatitude());
            addressDTO.setLongitude(address.getLongitude());
            dto.setAddress(addressDTO);
        }

        dto.setDescription(event.getDescription());
        dto.setMaxParticipants(event.getMaxParticipants());
        dto.setPublic(event.isPublic());

        return dto;
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

    public CreatedEventOverviewDTO updateEvent(int eventId, UpdateEventDTO dto) {
        // Fetch the existing event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

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

    public CreatedActivityDTO updateAgenda(int eventId, CreateActivityDTO dto) {
        // Fetch the existing event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Create a new Activity entity from the DTO
        Activity activity = new Activity();
        activity.setTitle(dto.getTitle());
        activity.setDescription(dto.getDescription());
        activity.setStartTime(dto.getStartTime());
        activity.setEndTime(dto.getEndTime());
        activity.setAddress(dto.getAddress());

        // Save the activity (if using a repository for Activity)
        activity = activityRepository.save(activity);

        // Append the new activity to the event's list of activities
        event.getActivities().add(activity);

        // Save the updated event
        Event savedEvent = eventRepository.save(event);

        // Map the saved Activity to CreatedActivityDTO
        CreatedActivityDTO createdActivityDTO = new CreatedActivityDTO();
        createdActivityDTO.setId(activity.getId());
        createdActivityDTO.setTitle(activity.getTitle());
        createdActivityDTO.setDescription(activity.getDescription());
        createdActivityDTO.setStartTime(activity.getStartTime());
        createdActivityDTO.setEndTime(activity.getEndTime());
        createdActivityDTO.setAddress(activity.getAddress());

        return createdActivityDTO;
    }

    public List<ActivityOverviewDTO> getAgenda(int eventId) {
        // Fetch the existing event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return event.getActivities().stream().map(this::mapToActivityOverviewDTO).toList();
    }

    private ActivityOverviewDTO mapToActivityOverviewDTO(Activity activity) {
        ActivityOverviewDTO dto = new ActivityOverviewDTO();
        dto.setId(activity.getId());
        dto.setTitle(activity.getTitle());
        dto.setDescription(activity.getDescription());
        dto.setStartTime(activity.getStartTime());
        dto.setEndTime(activity.getEndTime());
        dto.setAddress(activity.getAddress());
        return dto;
    }

    public List<ActivityOverviewDTO> deleteActivity(int eventId, int activityId) {
        // Fetch the existing event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Find and remove the activity from the event
        Activity activityToRemove = event.getActivities().stream()
                .filter(activity -> activity.getId() == activityId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        event.getActivities().remove(activityToRemove);
        eventRepository.save(event); // Save the updated event

        // Optionally delete the activity from the database if it's no longer needed
        activityRepository.deleteById(activityId);

        // Map the updated list of activities to ActivityOverviewDTO
        return event.getActivities().stream()
                .map(this::mapToActivityOverviewDTO)
                .toList();
    }
}
