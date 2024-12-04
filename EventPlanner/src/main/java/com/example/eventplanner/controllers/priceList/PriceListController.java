package com.example.eventplanner.controllers.priceList;

import com.example.eventplanner.dto.priceList.PriceListResponseDTO;
import com.example.eventplanner.dto.priceList.UpdatePriceListItemRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/priceList")
public class PriceListController {

    @GetMapping()
    public ResponseEntity<PriceListResponseDTO> GetAll() {
        return ResponseEntity.ok(new PriceListResponseDTO());
    }

    @PutMapping()
    public ResponseEntity<PriceListResponseDTO> Update(@RequestBody UpdatePriceListItemRequestDTO request) {
        return ResponseEntity.ok(new PriceListResponseDTO());
    }

}
