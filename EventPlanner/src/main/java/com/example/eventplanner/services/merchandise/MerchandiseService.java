package com.example.eventplanner.services.merchandise;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.category.GetAllCategoriesDTO;
import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseDetailDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.MerchandisePhotoDTO;
import com.example.eventplanner.dto.merchandise.review.MerchandiseReviewOverviewDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import com.example.eventplanner.model.merchandise.Review;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;
    private final CategoryRepository categoryRepository;

    public List<MerchandiseOverviewDTO> getTop() {
        return merchandiseRepository.findAll().stream()
                .filter(Merchandise::isAvailable)
                .map(this::convertToOverviewDTO)
                .sorted(Comparator.comparing(MerchandiseOverviewDTO::getRating).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public Page<MerchandiseOverviewDTO> getAll(Pageable pageable) {
        return merchandiseRepository.findAll(pageable)
                .map(this::convertToOverviewDTO);
    }

    private MerchandiseOverviewDTO convertToOverviewDTO(Merchandise merchandise) {
        MerchandiseOverviewDTO dto = new MerchandiseOverviewDTO();
        dto.setId(merchandise.getId());
        dto.setTitle(merchandise.getTitle());
        dto.setDescription(merchandise.getDescription());
        dto.setAddress(merchandise.getAddress());
        dto.setCategory(merchandise.getCategory().getTitle());
        if(merchandise.getPhotos() != null && !merchandise.getPhotos().isEmpty())
            dto.setPhotos(merchandise.getPhotos().stream().map(this::mapToMerchandisePhotoDTO).toList());
        dto.setRating(merchandise.getRating());
        dto.setType(merchandise.getClass().getSimpleName());
        dto.setPrice(merchandise.getPrice());
        return dto;
    }

    private MerchandisePhotoDTO mapToMerchandisePhotoDTO(MerchandisePhoto photo){
        MerchandisePhotoDTO dto = new MerchandisePhotoDTO();
        dto.setId(photo.getId());
        dto.setPhoto(photo.getPhoto());
        return dto;
    }

    public GetAllCategoriesDTO getAllCategories(){
        GetAllCategoriesDTO allCategoriesDTO = new GetAllCategoriesDTO();
        allCategoriesDTO.setCategories(categoryRepository.findAll().stream().map(this::convertToCategoryOverviewDTO).toList());
        return allCategoriesDTO;
    }

    private CategoryOverviewDTO convertToCategoryOverviewDTO(Category category) {
        CategoryOverviewDTO categoryDTO = new CategoryOverviewDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setTitle(category.getTitle());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }

    public MerchandiseDetailDTO getMerchandiseById(int id) {
        Merchandise merchandise = merchandiseRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Merchandise with id " + id + " not found")
        );
        return mapToMerchandiseDetails(merchandise);
    }

    private MerchandiseDetailDTO mapToMerchandiseDetails(Merchandise merchandise) {
        MerchandiseDetailDTO merchandiseDetails = new MerchandiseDetailDTO();
        merchandiseDetails.setId(merchandise.getId());
        merchandiseDetails.setTitle(merchandise.getTitle());
        merchandiseDetails.setDescription(merchandise.getDescription());
        merchandiseDetails.setSpecificity(merchandise.getSpecificity());
        merchandiseDetails.setPrice(merchandise.getPrice());
        merchandiseDetails.setDiscount(merchandise.getDiscount());
        merchandiseDetails.setVisible(merchandise.isVisible());
        merchandiseDetails.setAvailable(merchandise.isAvailable());
        merchandiseDetails.setMinDuration(merchandise.getMinDuration());
        merchandiseDetails.setMaxDuration(merchandise.getMaxDuration());
        merchandiseDetails.setReservationDeadline(merchandise.getReservationDeadline());
        merchandiseDetails.setCancellationDeadline(merchandise.getCancellationDeadline());
        merchandiseDetails.setMerchandisePhotos(merchandise.getPhotos().stream().map(this::mapToMerchandisePhotoDTO).toList());
        merchandiseDetails.setReviews(merchandise.getReviews().stream().map(this::mapToMerchandiseReviewDTO).toList());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity(merchandise.getAddress().getCity());
        addressDTO.setStreet(merchandise.getAddress().getStreet());
        addressDTO.setNumber(merchandise.getAddress().getNumber());
        addressDTO.setLongitude(merchandise.getAddress().getLongitude());
        addressDTO.setLatitude(merchandise.getAddress().getLatitude());
        merchandiseDetails.setAddress(addressDTO);

        CategoryOverviewDTO categoryOverviewDTO = new CategoryOverviewDTO();
        categoryOverviewDTO.setId(merchandise.getCategory().getId());
        categoryOverviewDTO.setTitle(merchandise.getCategory().getTitle());
        categoryOverviewDTO.setDescription(merchandise.getCategory().getDescription());
        categoryOverviewDTO.setPending(merchandise.getCategory().isPending());
        merchandiseDetails.setCategory(categoryOverviewDTO);

        merchandiseDetails.setEventTypes(merchandise.getEventTypes().stream().map(this::mapToEventTypeOverviewDTO).toList());
        merchandiseDetails.setRating(merchandise.getRating());
        return merchandiseDetails;
    }

    private EventTypeOverviewDTO mapToEventTypeOverviewDTO(EventType eventType) {
        EventTypeOverviewDTO dto = new EventTypeOverviewDTO();
        dto.setId(eventType.getId());
        dto.setTitle(eventType.getTitle());
        dto.setDescription(eventType.getDescription());
        dto.setActive(eventType.isActive());
        return dto;
    }

    private MerchandiseReviewOverviewDTO mapToMerchandiseReviewDTO(Review review) {
        MerchandiseReviewOverviewDTO dto = new MerchandiseReviewOverviewDTO();
        dto.setReviewersUsername(review.getReviewer().getUsername());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        return dto;
    }
}
