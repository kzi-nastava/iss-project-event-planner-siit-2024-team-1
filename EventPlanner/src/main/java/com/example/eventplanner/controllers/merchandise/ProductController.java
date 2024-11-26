package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
public class ProductController {
    @GetMapping("/{id}")
    public ResponseEntity<GetProductByIdResponseDTO> GetById(@PathVariable( value = "id") long id){
        return ResponseEntity.ok(new GetProductByIdResponseDTO());
    }
    @PostMapping("/{id}/buy")
    public ResponseEntity<com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO> buyProduct(@PathVariable( value = "id") int id) {
        return ResponseEntity.ok(new com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO());
    }
}