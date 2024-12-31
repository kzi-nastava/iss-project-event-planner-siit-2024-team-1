package com.example.eventplanner.controllers.priceList;

import com.example.eventplanner.dto.priceList.PriceListItemResponseDTO;
import com.example.eventplanner.dto.priceList.UpdatePriceListItemRequestDTO;
import com.example.eventplanner.services.priceList.PriceListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/priceList")
@RequiredArgsConstructor
public class PriceListController {
    private final PriceListService priceListService;

    @GetMapping("/{serviceProviderId}")
    public ResponseEntity<List<PriceListItemResponseDTO>> getPriceList(@PathVariable int serviceProviderId) {
        List<PriceListItemResponseDTO> priceList = priceListService.getPriceList(serviceProviderId);
        return ResponseEntity.ok(priceList);
    }

    @PutMapping("/update/{merchandiseId}/{serviceProviderId}")
    public ResponseEntity<List<PriceListItemResponseDTO>> updatePriceListItem(@PathVariable(name = "merchandiseId") int merchandiseId,
                                                                              @PathVariable(name = "serviceProviderId") int serviceProviderId,
                                                                              @RequestBody UpdatePriceListItemRequestDTO request) {
        List<PriceListItemResponseDTO> priceList = priceListService.updatePriceListItem(serviceProviderId, merchandiseId, request);
        return ResponseEntity.ok(priceList);
    }

}
