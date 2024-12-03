package com.example.eventplanner.controllers.merchandise;


import com.example.eventplanner.dto.filter.ProductFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseResponseDTO;
import com.example.eventplanner.services.event.EventService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

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
    public ResponseEntity<List<ProductOverviewDTO>> getAllBySpId(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(productService.getAllBySpId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProductByIdResponseDTO> getById(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok(new GetProductByIdResponseDTO());
    }

    @PostMapping()
    public ResponseEntity<CreateProductResponseDTO> create(@RequestBody CreateProductRequestDTO request) {
        return new ResponseEntity<>(productService.createProduct(request), HttpStatus.CREATED);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseOverviewDTO>> filterProducts(
            @RequestBody ProductFiltersDTO productFilters,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {

        List<MerchandiseOverviewDTO> emptyDTOs = Collections.nCopies(pageable.getPageSize(), new MerchandiseOverviewDTO());
        Page<MerchandiseOverviewDTO> merchandiseDTOs = new PageImpl<>(emptyDTOs, pageable, 0);

        return ResponseEntity.ok(merchandiseDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreateProductResponseDTO> update(@PathVariable int id, @RequestBody UpdateProductRequestDTO request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
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
