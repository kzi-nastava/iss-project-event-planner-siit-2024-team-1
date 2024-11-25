package com.example.eventplanner.controllers.merchandise;

import com.example.eventplanner.dto.merchandise.product.GetAllProductsResponseDTO;
import com.example.eventplanner.dto.merchandise.product.GetProductByIdResponseDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.create.CreateProductResponseDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductRequestDTO;
import com.example.eventplanner.dto.merchandise.product.update.UpdateProductResponseDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseResponseDTO;
import com.example.eventplanner.model.event.Category;
import com.example.eventplanner.model.event.EventType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @GetMapping()
    public GetAllProductsResponseDTO GetAll(){
        return new GetAllProductsResponseDTO();
    }

    @GetMapping("/sp/{id}")
    public GetAllProductsResponseDTO GetAllBySpId(@PathVariable( value = "id") int id){
        return new GetAllProductsResponseDTO();
    }

    @GetMapping("/{id}")
    public GetProductByIdResponseDTO GetById(@PathVariable( value = "id") int id){
        return new GetProductByIdResponseDTO();
    }

    @GetMapping("/filter")
    public GetAllProductsResponseDTO GetFiltered(@RequestParam Category category, @RequestParam EventType eventType, @RequestParam double price,
                                                      @RequestParam boolean available, @RequestParam String description){
        return new GetAllProductsResponseDTO();
    }

    @GetMapping("/{title}")
    public GetProductByIdResponseDTO GetByTitle(@PathVariable( value = "title") String title){
        return new GetProductByIdResponseDTO();
    }

    @PostMapping()
    public CreateProductResponseDTO Create(@RequestBody CreateProductRequestDTO request) {
        return new CreateProductResponseDTO();
    }

    @PutMapping()
    public UpdateProductResponseDTO Update(@RequestBody UpdateProductRequestDTO request) {
        return new UpdateProductResponseDTO();
    }

    @DeleteMapping("/{id}")
    public String Delete(@PathVariable int id){
        return "success";
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<ReviewMerchandiseResponseDTO> addReview(@RequestBody ReviewMerchandiseRequestDTO request, @PathVariable int id) {
        return ResponseEntity.ok(new ReviewMerchandiseResponseDTO());
    }
}