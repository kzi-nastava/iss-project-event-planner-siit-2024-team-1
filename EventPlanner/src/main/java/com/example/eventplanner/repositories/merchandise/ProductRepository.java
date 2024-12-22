package com.example.eventplanner.repositories.merchandise;

import com.example.eventplanner.model.merchandise.Product;
import com.example.eventplanner.model.merchandise.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p WHERE p.category.id IN :categoryIds AND p.available = true AND p.deleted = false")
    List<Product> findAllByCategories(@Param("categoryIds") List<Integer> categoryIds);
}
