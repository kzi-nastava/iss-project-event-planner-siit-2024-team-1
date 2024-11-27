package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.filter.ProductFiltersDTO;
import com.example.eventplanner.dto.merchandise.MerchandiseOverviewDTO;
import com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping("/{id}")
    public ResponseEntity<GetProductByIdResponseDTO> GetById(@PathVariable( value = "id") long id){
        return ResponseEntity.ok(new GetProductByIdResponseDTO());
    }
    @PostMapping("/{id}/buy")
    public ResponseEntity<com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO> buyProduct(@PathVariable( value = "id") int id) {
        return ResponseEntity.ok(new com.example.eventplanner.dto.merchandise.product.BuyProductResponseDTO());
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
}