package com.example.eventplanner.controllers.merchandise;


import com.example.eventplanner.dto.category.GetAllByCategoriesDTO;
import com.example.eventplanner.dto.filter.ProductFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseResponseDTO;

import com.example.eventplanner.dto.merchandise.service.ServiceOverviewDTO;
import com.example.eventplanner.services.merchandise.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.example.eventplanner.dto.merchandise.product.*;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<List<MerchandiseOverviewDTO>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/sp/{id}")
    public ResponseEntity<List<ProductOverviewDTO>> getAllBySpId(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(productService.getAllBySpId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOverviewDTO> getById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<CreateProductResponseDTO> create(@RequestBody CreateProductRequestDTO request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }

    @PostMapping("/get-by-categories")
    public ResponseEntity<List<MerchandiseOverviewDTO>> GetAllByCategories(@RequestBody GetAllByCategoriesDTO dto) {
        return ResponseEntity.ok(productService.getAllByCategories(dto.getCategories()));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseOverviewDTO>> filterProducts(
            @RequestParam int userId,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer durationMin,
            @RequestParam(required = false) Integer durationMax,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {

        ProductFiltersDTO productFiltersDTO=new ProductFiltersDTO(priceMin,priceMax,category,durationMin,durationMax,city);

        return ResponseEntity.ok(productService.search(userId,productFiltersDTO,search,pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateProductResponseDTO> update(@PathVariable int id, @RequestBody UpdateProductRequestDTO request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @PutMapping("/avail/{id}")
    public ResponseEntity<Boolean> avail(@PathVariable int id) {
        return ResponseEntity.ok(productService.availProduct(id));
    }

    @PutMapping("/show/{id}")
    public ResponseEntity<Boolean> show(@PathVariable int id) {
        return ResponseEntity.ok(productService.showProduct(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @PostMapping("/buy/{productId}")
    public ResponseEntity<Object> buyProduct(@PathVariable(value = "productId") int productId, @RequestBody int eventId) {
        try {
            productService.buyProduct(productId, eventId);
        }catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
        return ResponseEntity.ok(Map.of("message", "Product bought"));
    }

}
