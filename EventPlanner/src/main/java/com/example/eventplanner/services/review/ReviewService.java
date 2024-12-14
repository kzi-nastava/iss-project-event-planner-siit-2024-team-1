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
        return pendingReviews.stream().map(review -> {
            String reviewedType = null; // Determines if review is for Event, Product, or Service
            String reviewedTitle = null;

            // Find if the review is associated with an Event
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

            // Map to DTO
            return new ReviewOverviewDTO(
                    review.getId(),
                    review.getComment(),
                    review.getRating(),
                    review.getStatus(),
                    reviewedType,
                    reviewedTitle,
                    review.getReviewer().getUsername(),
                    review.getCreatedAt()
            );
        }).collect(Collectors.toList());
    }
}