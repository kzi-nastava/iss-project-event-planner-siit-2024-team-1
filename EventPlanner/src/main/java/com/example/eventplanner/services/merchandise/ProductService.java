package com.example.eventplanner.services.merchandise;

import com.example.eventplanner.dto.category.CategoryOverviewDTO;
import com.example.eventplanner.dto.eventType.EventTypeOverviewDTO;
import com.example.eventplanner.dto.filter.ProductFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.*;
import com.example.eventplanner.model.merchandise.*;
import com.example.eventplanner.model.user.EventOrganizer;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.ProductRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.ActivityOverviewDTO;
import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.merchandise.CreateMerchandisePhotoDTO;
import com.example.eventplanner.dto.merchandise.MerchandisePhotoDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductRequestDTO;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Activity;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Product;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.category.CategoryRepository;
import com.example.eventplanner.repositories.eventType.EventTypeRepository;
import com.example.eventplanner.repositories.merchandise.MerchandisePhotoRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.repositories.merchandise.ProductRepository;
import com.example.eventplanner.repositories.merchandise.ServiceRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandisePhotoRepository merchandisePhotoRepository;
    private final EventTypeRepository eventTypeRepository;
    private final CategoryRepository categoryRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final EventRepository eventRepository;

    public Page<MerchandiseOverviewDTO> search(int userId, ProductFiltersDTO productFiltersDTO, String search, Pageable pageable) {
        // Fetch user details
        User currentUser = fetchUserDetails(userId);
        List<User> blockedUsers = currentUser != null ? currentUser.getBlockedUsers().stream().filter(u->u instanceof ServiceProvider).toList() : List.of();

        // Create a specification for filtering
        Specification<Product> spec = createSpecification(productFiltersDTO, search)
                .and(excludeBlockedProviders(currentUser, blockedUsers)); // Exclude products by blocked providers

        // Fetch paginated products with the composed specification
        Page<Product> pagedProducts = productRepository.findAll(spec, pageable);

        // Convert the results to DTOs
        return pagedProducts.map(this::convertToOverviewDTO);
    }

    private Specification<Product> excludeBlockedProviders(User currentUser, List<User> blockedUsers) {
        return (root, query, criteriaBuilder) -> {
            if (currentUser == null || blockedUsers == null || blockedUsers.isEmpty()||!(currentUser instanceof EventOrganizer)) {
                return criteriaBuilder.conjunction(); // No filter if there are no blocked users or no current user
            }

            // Create a subquery to find services of blocked service providers
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<ServiceProvider> serviceProviderRoot = subquery.from(ServiceProvider.class);

            // Join service provider's merchandise (services)
            Join<ServiceProvider, Merchandise> merchandiseJoin = serviceProviderRoot.join("merchandise");

            // Select IDs of services belonging to blocked service providers
            subquery.select(merchandiseJoin.get("id"))
                    .where(serviceProviderRoot.in(blockedUsers));

            // Exclude services that are in the subquery of blocked service providers
            return criteriaBuilder.not(root.get("id").in(subquery));
        };
    }


    private User fetchUserDetails(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<MerchandiseOverviewDTO> getAll(){
        return productRepository.findAll().stream().map(this::convertToOverviewDTO).toList();
    }
    public ProductOverviewDTO getById(int id){
        return mapToProductOverviewDTO((Product)merchandiseRepository.findById(id).orElseThrow());
    }

    private Specification<Product> createSpecification(ProductFiltersDTO productFiltersDTO, String search) {
        Specification<Product> spec = Specification.where(null);
        spec = spec.and((root, query, criteriaBuilder) ->
                criteriaBuilder.isFalse(root.get("deleted"))
        );
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
            dto.setPhotos(product.getPhotos().stream().map(this::mapToMerchandisePhotoDTO).toList());
        dto.setRating(product.getRating());
        dto.setType(product.getClass().getSimpleName());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public List<ProductOverviewDTO> getAllBySpId(int spId) {
        ServiceProvider serviceProvider = (ServiceProvider) serviceProviderRepository.findById(spId)
                .orElseThrow(() -> new RuntimeException("Service provider not found"));

        // Map the merchandise of the service provider to ProductOverviewDTO
        return serviceProvider.getMerchandise().stream()
                .filter(merchandise -> merchandise instanceof Product && !merchandise.isDeleted()) 
                .map(this::mapToProductOverviewDTO)
                .toList();
    }
    // Helper method to map Merchandise to ProductOverviewDTO
    private ProductOverviewDTO mapToProductOverviewDTO(Merchandise savedProduct) {
        ProductOverviewDTO responseDTO = new ProductOverviewDTO();

        responseDTO.setId(savedProduct.getId());
        responseDTO.setTitle(savedProduct.getTitle());
        responseDTO.setDescription(savedProduct.getDescription());
        responseDTO.setSpecificity(savedProduct.getSpecificity());
        responseDTO.setPrice(savedProduct.getPrice());
        responseDTO.setDiscount(savedProduct.getDiscount());
        responseDTO.setVisible(savedProduct.isVisible());
        responseDTO.setAvailable(savedProduct.isAvailable());
        responseDTO.setMinDuration(savedProduct.getMinDuration());
        responseDTO.setMaxDuration(savedProduct.getMaxDuration());
        responseDTO.setReservationDeadline(savedProduct.getReservationDeadline());
        responseDTO.setCancellationDeadline(savedProduct.getCancellationDeadline());
        responseDTO.setAutomaticReservation(savedProduct.isAutomaticReservation());

        // Map the photos (from DTOs)
        responseDTO.setMerchandisePhotos(savedProduct.getPhotos().stream().map(this::mapToMerchandisePhotoDTO).collect(Collectors.toList()));

        // Map the Event Types
        responseDTO.setEventTypes(savedProduct.getEventTypes().stream().map(this::converToEventTypeOverviewDTO).toList());

        // Map the Category
        responseDTO.setCategory(convertToCategoryOverviewDTO(savedProduct.getCategory()));

        // Map the Address
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(savedProduct.getAddress().getStreet());
        addressDTO.setCity(savedProduct.getAddress().getCity());
        addressDTO.setNumber(savedProduct.getAddress().getNumber());
        addressDTO.setLatitude(savedProduct.getAddress().getLatitude());
        addressDTO.setLongitude(savedProduct.getAddress().getLongitude());
        responseDTO.setAddress(addressDTO);

        return responseDTO;
    }

    private EventTypeOverviewDTO converToEventTypeOverviewDTO(EventType eventType) {
        EventTypeOverviewDTO responseDTO = new EventTypeOverviewDTO();
        responseDTO.setId(eventType.getId());
        responseDTO.setTitle(eventType.getTitle());
        responseDTO.setDescription(eventType.getDescription());
        responseDTO.setActive(eventType.isActive());
        return responseDTO;
    }

    private CategoryOverviewDTO convertToCategoryOverviewDTO(Category category){
        CategoryOverviewDTO responseDTO = new CategoryOverviewDTO();
        responseDTO.setId(category.getId());
        responseDTO.setTitle(category.getTitle());
        responseDTO.setDescription(category.getDescription());
        responseDTO.setPending(category.isPending());
        return responseDTO;
    }

    public CreateProductResponseDTO createProduct(CreateProductRequestDTO createProductRequestDTO) {
        // Step 1: Fetch the ServiceProvider
        ServiceProvider serviceProvider = serviceProviderRepository.findById(createProductRequestDTO.getServiceProviderId())
                .orElseThrow(() -> new RuntimeException("Service provider not found"));

        // Step 2: Create a new Product (Merchandise)
        Product product = new Product();
        product.setTitle(createProductRequestDTO.getTitle());
        product.setDescription(createProductRequestDTO.getDescription());
        product.setSpecificity(createProductRequestDTO.getSpecificity());
        product.setPrice(createProductRequestDTO.getPrice());
        product.setDiscount(createProductRequestDTO.getDiscount());
        product.setVisible(createProductRequestDTO.isVisible());
        product.setAvailable(createProductRequestDTO.isAvailable());
        product.setMinDuration(createProductRequestDTO.getMinDuration());
        product.setMaxDuration(createProductRequestDTO.getMaxDuration());
        product.setReservationDeadline(createProductRequestDTO.getReservationDeadline());
        product.setCancellationDeadline(createProductRequestDTO.getCancellationDeadline());
        product.setAutomaticReservation(createProductRequestDTO.isAutomaticReservation());
        product.setPhotos(merchandisePhotoRepository.findAllById(createProductRequestDTO.getMerchandisePhotos()));

        // Step 4: Map Event Types
        List<EventType> eventTypes = eventTypeRepository.findAllById(createProductRequestDTO.getEventTypesIds());
        product.setEventTypes(eventTypes);


        Category category = categoryRepository.findById(createProductRequestDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);
        if(product.getCategory().isPending()){
            product.setState(MerchandiseState.PENDING);
        }else{
            product.setState(MerchandiseState.APPROVED);
        }


        // Step 6: Set Address
        Address address = new Address();
        address.setStreet(createProductRequestDTO.getAddress().getStreet());
        address.setCity(createProductRequestDTO.getAddress().getCity());
        address.setNumber(createProductRequestDTO.getAddress().getNumber());
        address.setLatitude(createProductRequestDTO.getAddress().getLatitude());
        address.setLongitude(createProductRequestDTO.getAddress().getLongitude());
        product.setAddress(address);


        ServiceProviderDTO serviceProviderDTO = new ServiceProviderDTO();
        serviceProviderDTO.setId(serviceProvider.getId());
        serviceProviderDTO.setName(serviceProvider.getName());
        serviceProviderDTO.setSurname(serviceProvider.getSurname());

        // Step 7: Save the new product (Merchandise)
        Product savedProduct = productRepository.save(product);

        List<Merchandise> merchandise = serviceProvider.getMerchandise();
        merchandise.add(savedProduct);
        serviceProvider.setMerchandise(merchandise);
        ServiceProvider sp = serviceProviderRepository.save(serviceProvider);


        // Step 8: Map to CreateProductResponseDTO and return
        return mapToCreateProductResponseDTO(savedProduct, serviceProviderDTO, savedProduct.getPhotos().stream().map(this::mapToMerchandisePhotoDTO).toList());
    }

    public CreateProductResponseDTO updateProduct(int productId, UpdateProductRequestDTO updateProductRequestDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setAvailable(false);
        Product oldProduct = productRepository.save(product);
        Product newProduct = new Product();

        // Step 1: Fetch the ServiceProvider
        ServiceProvider serviceProvider = serviceProviderRepository.findById(updateProductRequestDTO.getServiceProviderId())
                .orElseThrow(() -> new RuntimeException("Service provider not found"));
        ServiceProviderDTO serviceProviderDTO = new ServiceProviderDTO();
        serviceProviderDTO.setId(serviceProvider.getId());
        serviceProviderDTO.setName(serviceProvider.getName());
        serviceProviderDTO.setSurname(serviceProvider.getSurname());

        newProduct.setTitle(updateProductRequestDTO.getTitle());
        newProduct.setDescription(updateProductRequestDTO.getDescription());
        newProduct.setSpecificity(updateProductRequestDTO.getSpecificity());
        newProduct.setPrice(updateProductRequestDTO.getPrice());
        newProduct.setDiscount(updateProductRequestDTO.getDiscount());
        newProduct.setVisible(updateProductRequestDTO.isVisible());
        newProduct.setAvailable(true);
        newProduct.setMinDuration(updateProductRequestDTO.getMinDuration());
        newProduct.setMaxDuration(updateProductRequestDTO.getMaxDuration());
        newProduct.setReservationDeadline(updateProductRequestDTO.getReservationDeadline());
        newProduct.setCancellationDeadline(updateProductRequestDTO.getCancellationDeadline());
        newProduct.setAutomaticReservation(updateProductRequestDTO.isAutomaticReservation());
        newProduct.setPhotos(merchandisePhotoRepository.findAllById(updateProductRequestDTO.getMerchandisePhotos()));

        // Step 4: Map Event Types
        List<EventType> eventTypes = eventTypeRepository.findAllById(updateProductRequestDTO.getEventTypesIds());
        newProduct.setEventTypes(eventTypes);

        // Step 6: Set Address
        Address address = new Address();
        address.setStreet(updateProductRequestDTO.getAddress().getStreet());
        address.setCity(updateProductRequestDTO.getAddress().getCity());
        address.setNumber(updateProductRequestDTO.getAddress().getNumber());
        address.setLatitude(updateProductRequestDTO.getAddress().getLatitude());
        address.setLongitude(updateProductRequestDTO.getAddress().getLongitude());
        newProduct.setAddress(address);

        newProduct.setCategory(product.getCategory());

        // Step 7: Save the new product (Merchandise)
        Product savedProduct = productRepository.save(newProduct);

        List<Merchandise> merchandise = serviceProvider.getMerchandise();
        merchandise.add(savedProduct);
        serviceProvider.setMerchandise(merchandise);
        ServiceProvider sp = serviceProviderRepository.save(serviceProvider);

        // Step 8: Map to CreateProductResponseDTO and return
        return mapToCreateProductResponseDTO(savedProduct, serviceProviderDTO, savedProduct.getPhotos().stream().map(this::mapToMerchandisePhotoDTO).toList());
    }

    public MerchandisePhotoDTO mapToMerchandisePhotoDTO(MerchandisePhoto merchandisePhoto) {
        MerchandisePhotoDTO photoDTO = new MerchandisePhotoDTO();
        photoDTO.setId(merchandisePhoto.getId());  // Set the photo ID
        photoDTO.setPhoto(merchandisePhoto.getPhoto());  // Set the byte array of the photo

        return photoDTO;
    }

    private CreateProductResponseDTO mapToCreateProductResponseDTO(Merchandise savedProduct, ServiceProviderDTO serviceProvider, List<MerchandisePhotoDTO> photoDTOs) {
        CreateProductResponseDTO responseDTO = new CreateProductResponseDTO();

        responseDTO.setId(savedProduct.getId());
        responseDTO.setTitle(savedProduct.getTitle());
        responseDTO.setDescription(savedProduct.getDescription());
        responseDTO.setSpecificity(savedProduct.getSpecificity());
        responseDTO.setPrice(savedProduct.getPrice());
        responseDTO.setDiscount(savedProduct.getDiscount());
        responseDTO.setVisible(savedProduct.isVisible());
        responseDTO.setAvailable(savedProduct.isAvailable());
        responseDTO.setMinDuration(savedProduct.getMinDuration());
        responseDTO.setMaxDuration(savedProduct.getMaxDuration());
        responseDTO.setReservationDeadline(savedProduct.getReservationDeadline());
        responseDTO.setCancellationDeadline(savedProduct.getCancellationDeadline());
        responseDTO.setAutomaticReservation(savedProduct.isAutomaticReservation());

        // Map the Service Provider
        responseDTO.setServiceProvider(serviceProvider);

        // Map the photos (from DTOs)
        responseDTO.setMerchandisePhotos(photoDTOs);

        // Map the Event Types
        responseDTO.setEventTypes(savedProduct.getEventTypes().stream().map(this::converToEventTypeOverviewDTO).toList());

        // Map the Category
        responseDTO.setCategory(convertToCategoryOverviewDTO(savedProduct.getCategory()));

        // Map the Address
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(savedProduct.getAddress().getStreet());
        addressDTO.setCity(savedProduct.getAddress().getCity());
        addressDTO.setNumber(savedProduct.getAddress().getNumber());
        addressDTO.setLatitude(savedProduct.getAddress().getLatitude());
        addressDTO.setLongitude(savedProduct.getAddress().getLongitude());
        responseDTO.setAddress(addressDTO);

        return responseDTO;
    }

    public boolean deleteProduct(int productId) {
        // Fetch the existing event
        Merchandise product = merchandiseRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        product.setDeleted(true);
        merchandiseRepository.save(product);

        return true;
    }

    public BuyProductResponseDTO buyProduct(int productId, int eventId) throws Exception {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        boolean exists = event.getMerchandise().stream().anyMatch(
                p -> p.getCategory().getId() == product.getCategory().getId());
        if(exists) {
            throw new Exception("Already have product or service for this category");
        }
        else {
            event.getMerchandise().add(product);
            Event event1 = eventRepository.save(event);
        }

        return new BuyProductResponseDTO();
    }
}
