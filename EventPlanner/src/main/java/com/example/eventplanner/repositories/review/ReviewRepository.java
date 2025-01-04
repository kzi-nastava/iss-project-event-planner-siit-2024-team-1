package com.example.eventplanner.repositories.review;

import com.example.eventplanner.model.common.Review;
import com.example.eventplanner.model.merchandise.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByStatusAndDeletedFalse(ReviewStatus status); // To find all pending reviews
}