package com.example.eventplanner.services.merchandise;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.category.GetAllCategoriesDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.MerchandisePhotoDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
