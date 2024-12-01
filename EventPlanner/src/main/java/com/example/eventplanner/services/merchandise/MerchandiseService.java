package com.example.eventplanner.services.merchandise;

import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.model.merchandise.Merchandise;
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
            dto.setPhoto(merchandise.getPhotos().getFirst().getPhoto());
        dto.setRating(merchandise.getRating());
        dto.setType(merchandise.getClass().getSimpleName());
        dto.setPrice(merchandise.getPrice());
        return dto;
    }
}
