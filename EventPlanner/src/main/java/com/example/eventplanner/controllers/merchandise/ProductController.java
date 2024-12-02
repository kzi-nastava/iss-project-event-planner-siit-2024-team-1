package com.example.eventplanner.controllers.merchandise;


import com.example.eventplanner.dto.filter.ProductFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseResponseDTO;
import com.example.eventplanner.services.merchandise.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import com.example.eventplanner.dto.merchandise.product.*;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

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


    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseOverviewDTO>> filterProducts(
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer durationMin,
            @RequestParam(required = false) Integer durationMax,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {

        ProductFiltersDTO productFiltersDTO=new ProductFiltersDTO(priceMin,priceMax,category,durationMin,durationMax,city);

        return ResponseEntity.ok(productService.search(productFiltersDTO,search,pageable));
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

    @PostMapping("/{id}/review")
    public ResponseEntity<ReviewMerchandiseResponseDTO> addReview(@RequestBody ReviewMerchandiseRequestDTO request, @PathVariable int id) {
        return ResponseEntity.ok(new ReviewMerchandiseResponseDTO());
    }
}
