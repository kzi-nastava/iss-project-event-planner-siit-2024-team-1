package com.example.eventplanner.services.priceList;

import com.example.eventplanner.dto.priceList.PriceListItemResponseDTO;
import com.example.eventplanner.dto.priceList.UpdatePriceListItemRequestDTO;
import com.example.eventplanner.model.merchandise.Merchandise;
import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceListService {

    private final ServiceProviderRepository serviceProviderRepository;
    private final MerchandiseRepository merchandiseRepository;

    public List<PriceListItemResponseDTO> getPriceList(int serviceProviderId) {
        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(() -> new RuntimeException("Service provider not found"));
        return serviceProvider.getMerchandise().stream().map(this::mapToPriceListItemDTO).toList();
    }

    public List<PriceListItemResponseDTO> updatePriceListItem(int serviceProviderId, int merchandiseId, UpdatePriceListItemRequestDTO request) {
        Merchandise merchandise = merchandiseRepository.findById(merchandiseId)
                .orElseThrow(() -> new RuntimeException("Merchandise not found"));
        merchandise.setPrice(request.getPrice());
        merchandise.setDiscount(request.getDiscount());
        merchandiseRepository.save(merchandise);

        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceProviderId)
                .orElseThrow(() -> new RuntimeException("Service provider not found"));
        return serviceProvider.getMerchandise().stream().map(this::mapToPriceListItemDTO).toList();
    }

    private PriceListItemResponseDTO mapToPriceListItemDTO(Merchandise merchandise) {
        PriceListItemResponseDTO priceListItemResponseDTO = new PriceListItemResponseDTO();
        priceListItemResponseDTO.setMerchandiseId(merchandise.getId());
        priceListItemResponseDTO.setTitle(merchandise.getTitle());
        priceListItemResponseDTO.setPrice(merchandise.getPrice());
        priceListItemResponseDTO.setDiscount(merchandise.getDiscount());

        double discountedPrice = merchandise.getPrice() - (merchandise.getPrice()*merchandise.getDiscount())/100;
        priceListItemResponseDTO.setDiscountedPrice(discountedPrice);
        return priceListItemResponseDTO;
    }
}
