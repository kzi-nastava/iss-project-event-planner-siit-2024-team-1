package com.example.eventplanner.repositories.merchandise;


import com.example.eventplanner.model.merchandise.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer>, JpaSpecificationExecutor<Service> {
    @Query("SELECT s FROM Service s WHERE s.id = :serviceId AND s.available = true AND s.deleted = false")
    Optional<Service> findAvailableServiceById(@Param("serviceId") int serviceId);

    @Query("SELECT s FROM Service s WHERE s.category.id IN :categoryIds AND s.available = true AND s.deleted = false")
    List<Service> findAllByCategories(@Param("categoryIds") List<Integer> categoryIds);
}

