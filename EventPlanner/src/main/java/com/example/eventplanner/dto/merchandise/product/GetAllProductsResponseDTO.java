package com.example.eventplanner.dto.merchandise.product;

import com.example.eventplanner.model.merchandise.Product;

import java.util.List;

public class GetAllProductsResponseDTO {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
