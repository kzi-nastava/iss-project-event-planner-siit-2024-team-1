package com.example.eventplanner.repositories.category;

import com.example.eventplanner.model.event.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {
    @Query("SELECT c FROM Category c WHERE c.pending = false")
    List<Category> findAllApprovedCategories();

    @Query("SELECT c FROM Category c WHERE c.pending = true")
    List<Category> findAllPendingCategories();
}
