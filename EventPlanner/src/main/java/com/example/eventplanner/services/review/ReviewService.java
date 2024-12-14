package com.example.eventplanner.services.review;

import com.example.eventplanner.dto.merchandise.review.ReviewOverviewDTO;
import com.example.eventplanner.model.merchandise.*;
import com.example.eventplanner.model.event.Event;

import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.repositories.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final MerchandiseRepository merchandiseRepository;

    public List<ReviewOverviewDTO> getAllPendingReviews() {
        // Fetch all pending reviews
        List<Review> pendingReviews = reviewRepository.findByStatusAndDeletedFalse(ReviewStatus.PENDING);

        // Map each review to ReviewOverviewDTO
        return pendingReviews.stream().map(this::mapToReviewOverviewDTO).collect(Collectors.toList());
    }

    public ReviewOverviewDTO approveReview(int reviewId) {
        // Fetch the review by ID
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        // Update the review's status to APPROVED
        review.setStatus(ReviewStatus.APPROVED);
        reviewRepository.save(review);

        // Map the updated review to ReviewOverviewDTO
        return mapToReviewOverviewDTO(review);
    }

    public ReviewOverviewDTO denyReview(int reviewId) {
        // Fetch the review by ID
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        // Set the review's deleted flag to true
        review.setDeleted(true);
        reviewRepository.save(review);

        // Map the updated review to ReviewOverviewDTO
        return mapToReviewOverviewDTO(review);
    }

    private ReviewOverviewDTO mapToReviewOverviewDTO(Review review) {
        String reviewedType = null; // Determine if review is for Event, Product, or Service
        String reviewedTitle = null;

        // Check if the review is associated with an Event
        Event event = eventRepository.findByReviewsContaining(review);
        if (event != null) {
            reviewedType = "Event";
            reviewedTitle = event.getTitle();
        } else {
            // If not an Event, check for Merchandise (Product/Service)
            Merchandise merchandise = merchandiseRepository.findByReviewsContaining(review);
            if (merchandise instanceof Product) {
                reviewedType = "Product";
            } else if (merchandise instanceof com.example.eventplanner.model.merchandise.Service) {
                reviewedType = "Service";
            }
            if (merchandise != null) {
                reviewedTitle = merchandise.getTitle();
            }
        }

        // Map to ReviewOverviewDTO
        return new ReviewOverviewDTO(
                review.getId(),
                review.getComment(),
                review.getRating(),
                review.getStatus(),
                reviewedType,
                reviewedTitle,
                review.getReviewer() != null ? review.getReviewer().getUsername() : null,
                review.getCreatedAt()
        );
    }
}