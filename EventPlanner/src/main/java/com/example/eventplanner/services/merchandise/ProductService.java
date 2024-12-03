package com.example.eventplanner.services.merchandise;

import com.example.eventplanner.dto.common.AddressDTO;
import com.example.eventplanner.dto.event.ActivityOverviewDTO;
import com.example.eventplanner.dto.event.EventOverviewDTO;
import com.example.eventplanner.dto.merchandise.CreateMerchandisePhotoDTO;
import com.example.eventplanner.dto.merchandise.MerchandisePhotoDTO;
import com.example.eventplanner.dto.merchandise.product.GetAllProductsResponseDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.product.ProductOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductRequestDTO;
import com.example.eventplanner.model.common.Address;
import com.example.eventplanner.model.event.Activity;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.Event;
import com.example.eventplanner.model.event.EventType;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.merchandise.MerchandisePhoto;
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
    private final UserRepository userRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandisePhotoRepository merchandisePhotoRepository;
    private final EventTypeRepository eventTypeRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceProviderRepository serviceProviderRepository;

    public List<ProductOverviewDTO> getAllBySpId(int spId) {
        ServiceProvider serviceProvider = (ServiceProvider) serviceProviderRepository.findById(spId)
                .orElseThrow(() -> new RuntimeException("Service provider not found"));

        // Map the merchandise of the service provider to ProductOverviewDTO
        return serviceProvider.getMerchandise().stream()
                .map(this::mapToProductOverviewDTO).toList();
    }
    // Helper method to map Merchandise to ProductOverviewDTO
    private ProductOverviewDTO mapToProductOverviewDTO(Merchandise savedProduct) {
        ProductOverviewDTO responseDTO = new ProductOverviewDTO();

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
        responseDTO.setEventTypes(savedProduct.getEventTypes());

        // Map the Category
        responseDTO.setCategory(savedProduct.getCategory());

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

    public CreateProductResponseDTO createProduct(CreateProductRequestDTO createProductRequestDTO) {
        // Step 1: Fetch the ServiceProvider
        User serviceProvider = userRepository.findById(createProductRequestDTO.getServiceProviderId())
                .orElseThrow(() -> new RuntimeException("Service provider not found"));

        // Step 2: Create a new Product (Merchandise)
        Merchandise product = new Merchandise();
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

        // Step 3: Create and map Merchandise Photos
        List<MerchandisePhoto> photos = createProductRequestDTO.getMerchandisePhotos().stream()
                .map(photoDto -> {
                    MerchandisePhoto newPhoto = new MerchandisePhoto();
                    newPhoto.setPhoto(Base64.getDecoder().decode(photoDto.getPhoto()));// Assuming the photo ID is passed as part of DTO
                    return newPhoto;
                })
                .collect(Collectors.toList());

        // Save photos to the database first, before associating them with the product
        List<MerchandisePhoto> savedPhotos = merchandisePhotoRepository.saveAll(photos);
        product.setPhotos(savedPhotos);

        // Step 4: Map Event Types
        List<EventType> eventTypes = eventTypeRepository.findAllById(createProductRequestDTO.getEventTypesIds());
        product.setEventTypes(eventTypes);

        // Step 5: Set Category
        Category category = categoryRepository.findById(createProductRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        // Step 6: Set Address
        Address address = new Address();
        address.setStreet(createProductRequestDTO.getAddress().getStreet());
        address.setCity(createProductRequestDTO.getAddress().getCity());
        address.setNumber(createProductRequestDTO.getAddress().getNumber());
        address.setLatitude(createProductRequestDTO.getAddress().getLatitude());
        address.setLongitude(createProductRequestDTO.getAddress().getLongitude());
        product.setAddress(address);

        // Step 7: Save the new product (Merchandise)
        Merchandise savedProduct = merchandiseRepository.save(product);

        // Step 8: Map to CreateProductResponseDTO and return
        return mapToCreateProductResponseDTO(savedProduct, serviceProvider, savedPhotos.stream().map(this::mapToMerchandisePhotoDTO).toList());
    }

    public CreateProductResponseDTO updateProduct(int productId, UpdateProductRequestDTO updateProductRequestDTO) {
        Merchandise product = merchandiseRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Step 1: Fetch the ServiceProvider
        User serviceProvider = userRepository.findById(updateProductRequestDTO.getServiceProviderId())
                .orElseThrow(() -> new RuntimeException("Service provider not found"));

        product.setTitle(updateProductRequestDTO.getTitle());
        product.setDescription(updateProductRequestDTO.getDescription());
        product.setSpecificity(updateProductRequestDTO.getSpecificity());
        product.setPrice(updateProductRequestDTO.getPrice());
        product.setDiscount(updateProductRequestDTO.getDiscount());
        product.setVisible(updateProductRequestDTO.isVisible());
        product.setAvailable(updateProductRequestDTO.isAvailable());
        product.setMinDuration(updateProductRequestDTO.getMinDuration());
        product.setMaxDuration(updateProductRequestDTO.getMaxDuration());
        product.setReservationDeadline(updateProductRequestDTO.getReservationDeadline());
        product.setCancellationDeadline(updateProductRequestDTO.getCancellationDeadline());
        product.setAutomaticReservation(updateProductRequestDTO.isAutomaticReservation());

        // Step 3: Create and map Merchandise Photos
        List<MerchandisePhoto> photos = updateProductRequestDTO.getMerchandisePhotos().stream()
                .map(photoDto -> {
                    MerchandisePhoto newPhoto = new MerchandisePhoto();
                    newPhoto.setPhoto(Base64.getDecoder().decode(photoDto.getPhoto()));// Assuming the photo ID is passed as part of DTO
                    return newPhoto;
                })
                .collect(Collectors.toList());

        // Save photos to the database first, before associating them with the product
        List<MerchandisePhoto> savedPhotos = merchandisePhotoRepository.saveAll(photos);
        product.setPhotos(savedPhotos);

        // Step 4: Map Event Types
        List<EventType> eventTypes = eventTypeRepository.findAllById(updateProductRequestDTO.getEventTypesIds());
        product.setEventTypes(eventTypes);

        // Step 6: Set Address
        Address address = new Address();
        address.setStreet(updateProductRequestDTO.getAddress().getStreet());
        address.setCity(updateProductRequestDTO.getAddress().getCity());
        address.setNumber(updateProductRequestDTO.getAddress().getNumber());
        address.setLatitude(updateProductRequestDTO.getAddress().getLatitude());
        address.setLongitude(updateProductRequestDTO.getAddress().getLongitude());
        product.setAddress(address);

        // Step 7: Save the new product (Merchandise)
        Merchandise savedProduct = merchandiseRepository.save(product);

        // Step 8: Map to CreateProductResponseDTO and return
        return mapToCreateProductResponseDTO(savedProduct, serviceProvider, savedPhotos.stream().map(this::mapToMerchandisePhotoDTO).toList());
    }

    public MerchandisePhotoDTO mapToMerchandisePhotoDTO(MerchandisePhoto merchandisePhoto) {
        MerchandisePhotoDTO photoDTO = new MerchandisePhotoDTO();
        photoDTO.setId(merchandisePhoto.getId());  // Set the photo ID
        photoDTO.setPhoto(merchandisePhoto.getPhoto());  // Set the byte array of the photo

        return photoDTO;
    }

    private CreateProductResponseDTO mapToCreateProductResponseDTO(Merchandise savedProduct, User serviceProvider, List<MerchandisePhotoDTO> photoDTOs) {
        CreateProductResponseDTO responseDTO = new CreateProductResponseDTO();

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
        responseDTO.setEventTypes(savedProduct.getEventTypes());

        // Map the Category
        responseDTO.setCategory(savedProduct.getCategory());

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
        Merchandise product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.deleteById(product.getId());

        return true;
    }
}
