package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @PostMapping("/{id}/buy")
    public ResponseEntity<com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO> buyProduct(@PathVariable( value = "id") int id) {
        return ResponseEntity.ok(new com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO());
    }
}