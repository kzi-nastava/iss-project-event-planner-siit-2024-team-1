package com.example.eventplanner.repositories.merchandise;

import com.example.eventplanner.model.merchandise.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
