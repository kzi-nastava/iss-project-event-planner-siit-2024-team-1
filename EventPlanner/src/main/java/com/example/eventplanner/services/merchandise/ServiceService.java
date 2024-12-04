package com.example.eventplanner.services.merchandise;

import com.example.eventplanner.dto.filter.ServiceFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;

import com.example.eventplanner.dto.merchandise.service.ReservationRequestDTO;
import com.example.eventplanner.dto.merchandise.service.ReservationResponseDTO;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.merchandise.Timeslot;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.ServiceRepository;
import com.example.eventplanner.repositories.merchandise.TimeslotRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import com.example.eventplanner.services.email.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final EventRepository eventRepository;
    private final TimeslotRepository timeslotRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    public Page<MerchandiseOverviewDTO> search(ServiceFiltersDTO ServiceFiltersDTO, String search, Pageable pageable) {
        Specification<com.example.eventplanner.model.merchandise.Service> spec = createSpecification(ServiceFiltersDTO, search);
        Page<com.example.eventplanner.model.merchandise.Service> products = serviceRepository.findAll(spec, pageable);
        return products.map(this::convertToOverviewDTO);
    }

    private Specification<com.example.eventplanner.model.merchandise.Service> createSpecification(ServiceFiltersDTO ServiceFiltersDTO, String search) {
        Specification<com.example.eventplanner.model.merchandise.Service> spec = Specification.where(null);
        spec = addPriceRangeFilter(spec, ServiceFiltersDTO);
        spec = addCategoryFilter(spec, ServiceFiltersDTO);
        spec = addCityFilter(spec, ServiceFiltersDTO);
        spec = addGlobalSearch(spec, search);
        return spec;
    }

    private Specification<com.example.eventplanner.model.merchandise.Service> addPriceRangeFilter(Specification<com.example.eventplanner.model.merchandise.Service> spec, ServiceFiltersDTO ServiceFiltersDTO) {
        if (ServiceFiltersDTO.getPriceMin() != null && ServiceFiltersDTO.getPriceMax() != null) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("price"),
                            ServiceFiltersDTO.getPriceMin(),
                            ServiceFiltersDTO.getPriceMax())
            );
        }
        return spec;
    }

    private Specification<com.example.eventplanner.model.merchandise.Service> addCategoryFilter(Specification<com.example.eventplanner.model.merchandise.Service> spec, ServiceFiltersDTO ServiceFiltersDTO) {
        if (StringUtils.hasText(ServiceFiltersDTO.getCategory())) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("category"),
                            ServiceFiltersDTO.getCategory()
                    )
            );
        }
        return spec;
    }

    private Specification<com.example.eventplanner.model.merchandise.Service> addCityFilter(Specification<com.example.eventplanner.model.merchandise.Service> spec, ServiceFiltersDTO ServiceFiltersDTO) {
        if (StringUtils.hasText(ServiceFiltersDTO.getCity())) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("address").get("city"),
                            ServiceFiltersDTO.getCity()
                    )
            );
        }
        return spec;
    }

    private Specification<com.example.eventplanner.model.merchandise.Service> addGlobalSearch(Specification<com.example.eventplanner.model.merchandise.Service> spec, String search) {
        if (StringUtils.hasText(search)) {
            return spec.and((root, query, criteriaBuilder) -> {
                String searchPattern = "%" + search.toLowerCase() + "%";
                return criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("title")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("address").get("city")), searchPattern),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("address").get("street")), searchPattern)
                );
            });
        }
        return spec;
    }

    private MerchandiseOverviewDTO convertToOverviewDTO(com.example.eventplanner.model.merchandise.Service service) {
        MerchandiseOverviewDTO dto = new MerchandiseOverviewDTO();
        dto.setId(service.getId());
        dto.setTitle(service.getTitle());
        dto.setDescription(service.getDescription());
        dto.setAddress(service.getAddress());
        dto.setCategory(service.getCategory().getTitle());
        if(service.getPhotos() != null && !service.getPhotos().isEmpty())
            dto.setPhoto(service.getPhotos().getFirst().getPhoto());
        dto.setRating(service.getRating());
        dto.setType(service.getClass().getSimpleName());
        dto.setPrice(service.getPrice());
        return dto;
    }

    @Transactional
    public ReservationResponseDTO reserveService(int serviceId, ReservationRequestDTO request) {
        // Fetch the service
        com.example.eventplanner.model.merchandise.Service service = null;
        try {
            service = serviceRepository.findAvailableServiceById(serviceId)
                    .orElseThrow(() -> new Exception("Service not found or unavailable"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Fetch the event
        Event event = null;
        try {
            event = eventRepository.findById(request.getEventId())
                    .orElseThrow(() -> new Exception("Event not found"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Validate reservation timing
        try {
            validateReservationTiming(service, event, request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // Check time slot availability
        try {
            checkTimeSlotAvailability(service, request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // Calculate end time if not provided
        LocalDateTime endTime = calculateEndTime(service, request);

        // Create and save time slot
        Timeslot timeslot = new Timeslot(request.getStartTime(), endTime);

        service.getTimeslots().add(timeslot);

        event.getMerchandise().add(service);

        // Save changes
        timeslotRepository.save(timeslot);
        serviceRepository.save(service);
        eventRepository.save(event);

        // Send notifications
        sendReservationNotifications(service, event);
        sendReservationEmail(request,serviceId);

        return mapToReservationResponse(service,event,request);
    }

    private void validateReservationTiming(com.example.eventplanner.model.merchandise.Service service, Event event, ReservationRequestDTO request) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationDeadline = event.getDate().minusMinutes(service.getReservationDeadline());
        LocalDateTime cancellationDeadline = event.getDate().minusMinutes(service.getCancellationDeadline());

        // Validate start time is within reservation deadline
        if (request.getStartTime().isAfter(event.getDate()) ||
                request.getStartTime().isBefore(reservationDeadline)) {
            throw new Exception("Reservation is outside allowed time frame");
        }

        // Validate duration constraints
        validateDurationConstraints(service, request);
    }

    private void validateDurationConstraints(com.example.eventplanner.model.merchandise.Service service, ReservationRequestDTO request) throws Exception {
        // Calculate duration
        long durationMinutes = Duration.between(
                request.getStartTime(),
                request.getEndTime() != null ? request.getEndTime() : request.getStartTime().plusMinutes(service.getMinDuration())
        ).toMinutes();

        if (durationMinutes < service.getMinDuration() ||
                (service.getMaxDuration() > 0 && durationMinutes > service.getMaxDuration())) {
            throw new Exception("Service duration does not meet constraints");
        }
    }

    private void checkTimeSlotAvailability(com.example.eventplanner.model.merchandise.Service service, ReservationRequestDTO request) throws Exception {
        LocalDateTime startTime = request.getStartTime();
        LocalDateTime endTime = request.getEndTime() != null
                ? request.getEndTime()
                : startTime.plusMinutes(service.getMinDuration());

        // Check for overlapping time slots within the service
        boolean isTimeSlotAvailable = service.getTimeslots().stream()
                .allMatch(existingSlot ->
                        endTime.isBefore(existingSlot.getStartTime()) ||
                                startTime.isAfter(existingSlot.getEndTime())
                );

        if (!isTimeSlotAvailable) {
            throw new Exception("Selected time slot is already booked");
        }
    }

    private LocalDateTime calculateEndTime(com.example.eventplanner.model.merchandise.Service service, ReservationRequestDTO request) {
        if (request.getEndTime() != null) {
            return request.getEndTime();
        }

        // If end time not provided, calculate based on service duration
        return request.getStartTime().plusMinutes(service.getMinDuration());
    }

    private void sendReservationNotifications(com.example.eventplanner.model.merchandise.Service service, Event event) {

    }

    private void sendReservationEmail(ReservationRequestDTO request,int serviceId) {
        Optional<User> eventOrganizer=userRepository.findById(request.getOrganizerId());
        if (eventOrganizer.isEmpty()) {
            throw new RuntimeException("Organizer not found");
        }
        Optional<ServiceProvider> serviceProvider=serviceProviderRepository.findByMerchandiseId(serviceId);
        if (serviceProvider.isEmpty()) {
            throw new RuntimeException("provider not found");
        }
        emailService.sendMail(
            "system@eventplanner.com",
                serviceProvider.get().getUsername(),
                "Reservation for event "+ request.getEventId(),
                "Reservation successful"

        );
        emailService.sendMail(
                "system@eventplanner.com",
                eventOrganizer.get().getUsername(),
                "Reservation for event "+ request.getEventId(),
                "Reservation successful"

        );
    }

    private ReservationResponseDTO mapToReservationResponse(com.example.eventplanner.model.merchandise.Service service,Event event,ReservationRequestDTO request) {
        ReservationResponseDTO response = new ReservationResponseDTO();
        response.setServiceId(service.getId());
        response.setEventId(event.getId());
        response.setProviderId(request.getOrganizerId());
        response.setStartTime(request.getStartTime());
        response.setEndTime(request.getEndTime());
        return response;
    }

}
