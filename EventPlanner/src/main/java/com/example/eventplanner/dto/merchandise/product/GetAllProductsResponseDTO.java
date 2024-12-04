package com.example.eventplanner.dto.merchandise.product;

import com.example.eventplanner.model.merchandise.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class GetAllProductsResponseDTO {
    private List<Product> products;

}
