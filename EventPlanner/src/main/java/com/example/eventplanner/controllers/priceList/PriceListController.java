package com.example.eventplanner.controllers.priceList;

import com.example.eventplanner.dto.priceList.PriceListResponseDTO;
import com.example.eventplanner.dto.priceList.UpdatePriceListItemRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/priceList")
public class PriceListController {

    @GetMapping()
    public PriceListResponseDTO GetAll() {
        return new PriceListResponseDTO();
    }

    @PutMapping()
    public PriceListResponseDTO Update(@RequestBody UpdatePriceListItemRequestDTO request) {
        return new PriceListResponseDTO();
    }

}
