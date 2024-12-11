package com.example.eventplanner.repositories.merchandise;

import com.example.eventplanner.model.merchandise.Merchandise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchandiseRepository extends JpaRepository<Merchandise, Integer> {
    @Query("SELECT m FROM Merchandise m WHERE m.category.id = :categoryId")
    List<Merchandise> findMerchandiseByCategory(@Param("categoryId") int categoryId);
}
