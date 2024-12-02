package com.example.eventplanner.repositories.eventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.eventplanner.model.event.EventType;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, Integer> {
}
