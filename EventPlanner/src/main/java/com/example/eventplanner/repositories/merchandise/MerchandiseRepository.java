package com.example.eventplanner.repositories.merchandise;

import com.example.eventplanner.model.merchandise.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchandiseRepository extends JpaRepository<Service, Integer> {

}
