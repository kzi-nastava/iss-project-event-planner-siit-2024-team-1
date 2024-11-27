package com.example.eventplanner.controllers.merchandise;


import com.example.eventplanner.dto.filter.ProductFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
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

@RestController
@RequestMapping("api/v1/products")
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


    @GetMapping("/search")
    public ResponseEntity<Page<MerchandiseOverviewDTO>> filterProducts(
            @RequestBody ProductFiltersDTO productFilters,
            @RequestParam(required = false) String search,
            @PageableDefault(size = 10) Pageable pageable) {

        List<MerchandiseOverviewDTO> emptyDTOs = Collections.nCopies(pageable.getPageSize(), new MerchandiseOverviewDTO());
        Page<MerchandiseOverviewDTO> merchandiseDTOs = new PageImpl<>(emptyDTOs, pageable, 0);

        return ResponseEntity.ok(merchandiseDTOs);
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
