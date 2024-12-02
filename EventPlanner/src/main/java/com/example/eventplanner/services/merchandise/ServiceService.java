package com.example.eventplanner.services.merchandise;

import com.example.eventplanner.dto.filter.ServiceFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;

import com.example.eventplanner.repositories.merchandise.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository serviceRepository;

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
}
