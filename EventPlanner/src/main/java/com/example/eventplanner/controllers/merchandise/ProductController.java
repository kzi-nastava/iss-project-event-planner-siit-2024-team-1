package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.merchandise.product.*;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @GetMapping()
    public ResponseEntity<GetAllProductsResponseDTO> getAll() {
        return ResponseEntity.ok(new GetAllProductsResponseDTO());
    }

    @GetMapping("/sp/{id}")
    public ResponseEntity<GetAllProductsResponseDTO> getAllBySpId(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(new GetAllProductsResponseDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProductByIdResponseDTO> getById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(new GetProductByIdResponseDTO());
    }

    @PostMapping()
    public ResponseEntity<CreateProductResponseDTO> create(@RequestBody CreateProductRequestDTO request) {
        return ResponseEntity.ok(new CreateProductResponseDTO());
    }

    @PutMapping()
    public ResponseEntity<UpdateProductResponseDTO> update(@RequestBody UpdateProductRequestDTO request) {
        return ResponseEntity.ok(new UpdateProductResponseDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok("success");
    }

    @PostMapping("/{id}/buy")
    public ResponseEntity<BuyProductResponseDTO> buyProduct(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(new BuyProductResponseDTO());
    }
}
