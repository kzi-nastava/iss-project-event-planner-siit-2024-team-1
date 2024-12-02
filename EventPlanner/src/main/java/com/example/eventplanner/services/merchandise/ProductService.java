package com.example.eventplanner.services.merchandise;

import com.example.eventplanner.dto.filter.ProductFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.model.merchandise.Product;
import com.example.eventplanner.repositories.merchandise.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<MerchandiseOverviewDTO> search(ProductFiltersDTO productFiltersDTO, String search, Pageable pageable) {
        Specification<Product> spec = createSpecification(productFiltersDTO, search);
        Page<Product> products = productRepository.findAll(spec, pageable);
        return products.map(this::convertToOverviewDTO);
    }

    private Specification<Product> createSpecification(ProductFiltersDTO productFiltersDTO, String search) {
        Specification<Product> spec = Specification.where(null);
        spec = addPriceRangeFilter(spec, productFiltersDTO);
        spec = addCategoryFilter(spec, productFiltersDTO);
        spec = addCityFilter(spec, productFiltersDTO);
        spec = addGlobalSearch(spec, search);
        return spec;
    }

    private Specification<Product> addPriceRangeFilter(Specification<Product> spec, ProductFiltersDTO productFiltersDTO) {
        if (productFiltersDTO.getPriceMin() != null && productFiltersDTO.getPriceMax() != null) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("price"),
                            productFiltersDTO.getPriceMin(),
                            productFiltersDTO.getPriceMax())
            );
        }
        return spec;
    }

    private Specification<Product> addCategoryFilter(Specification<Product> spec, ProductFiltersDTO productFiltersDTO) {
        if (StringUtils.hasText(productFiltersDTO.getCategory())) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("category"),
                            productFiltersDTO.getCategory()
                    )
            );
        }
        return spec;
    }

    private Specification<Product> addCityFilter(Specification<Product> spec, ProductFiltersDTO productFiltersDTO) {
        if (StringUtils.hasText(productFiltersDTO.getCity())) {
            return spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(
                            root.get("address").get("city"),
                            productFiltersDTO.getCity()
                    )
            );
        }
        return spec;
    }

    private Specification<Product> addGlobalSearch(Specification<Product> spec, String search) {
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

    private MerchandiseOverviewDTO convertToOverviewDTO(Product product) {
        MerchandiseOverviewDTO dto = new MerchandiseOverviewDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setDescription(product.getDescription());
        dto.setAddress(product.getAddress());
        dto.setCategory(product.getCategory().getTitle());
        if(product.getPhotos() != null && !product.getPhotos().isEmpty())
            dto.setPhoto(product.getPhotos().getFirst().getPhoto());
        dto.setRating(product.getRating());
        dto.setType(product.getClass().getSimpleName());
        dto.setPrice(product.getPrice());
        return dto;
    }


}
