package com.example.eventplanner.repositories.eventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.eventplanner.model.event.EventType;

import java.util.List;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
    @Query("SELECT et FROM EventType et JOIN et.categories c WHERE c.id = :categoryId")
    List<EventType> findEventTypesByCategoryId(@Param("categoryId") int categoryId);
}
