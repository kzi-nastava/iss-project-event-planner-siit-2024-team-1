package com.example.eventplanner.repositories.user;

import com.example.eventplanner.model.user.ServiceProvider;
import com.example.eventplanner.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Integer> {
    Optional<ServiceProvider> findByUsername(String username);
    @Query("SELECT sp FROM ServiceProvider sp JOIN sp.merchandise m WHERE m.id = :merchandiseId")
    Optional<ServiceProvider> findByMerchandiseId(@Param("merchandiseId") int merchandiseId);
}
