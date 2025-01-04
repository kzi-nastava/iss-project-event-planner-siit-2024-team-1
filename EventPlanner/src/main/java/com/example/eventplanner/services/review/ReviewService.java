package com.example.eventplanner.services.review;

import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseResponseDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewOverviewDTO;
import com.example.eventplanner.model.common.Review;
import com.example.eventplanner.model.merchandise.*;
import com.example.eventplanner.model.event.Event;

import com.example.eventplanner.model.user.User;
import com.example.eventplanner.repositories.event.EventRepository;
import com.example.eventplanner.repositories.merchandise.MerchandiseRepository;
import com.example.eventplanner.repositories.review.ReviewRepository;
import com.example.eventplanner.repositories.user.ServiceProviderRepository;
import com.example.eventplanner.repositories.user.UserRepository;
import com.example.eventplanner.services.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final EventRepository eventRepository;
    private final MerchandiseRepository merchandiseRepository;
    private final NotificationService notificationService;
    private final ServiceProviderRepository serviceProviderRepository;
    private final UserRepository userRepository;

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
        int reviewedEntityId;

        // Check if the review is associated with an Event
        Event event = eventRepository.findByReviewsContaining(review);
        int reviewedUserId;
        if (event != null) {
            reviewedType = "EVENT";
            reviewedTitle = event.getTitle();
            reviewedUserId=event.getOrganizer().getId();
            reviewedEntityId=event.getId();
        } else {
            // If not an Event, check for Merchandise (Product/Service)
            Merchandise merchandise = merchandiseRepository.findByReviewsContaining(review);
            reviewedUserId=serviceProviderRepository.findByMerchandiseId(merchandise.getId()).get().getId();
            if (merchandise instanceof Product) {
                reviewedType = "PRODUCT";
            } else if (merchandise instanceof com.example.eventplanner.model.merchandise.Service) {
                reviewedType = "SERVICE";
            }
            if (merchandise != null) {
                reviewedTitle = merchandise.getTitle();
            }
            reviewedEntityId=merchandise.getId();
        }
        ReviewOverviewDTO reviewOverviewDTO=new ReviewOverviewDTO(
                review.getId(),
                review.getComment(),
                review.getRating(),
                review.getStatus(),
                reviewedType,
                reviewedTitle,
                review.getReviewer() != null ? review.getReviewer().getUsername() : null,
                review.getCreatedAt()
        );
        // Map to ReviewOverviewDTO
        if(reviewOverviewDTO.getStatus()==ReviewStatus.APPROVED)
            notificationService.notifyOfNewReview(reviewedUserId, reviewOverviewDTO,reviewedEntityId);
        return reviewOverviewDTO;
    }

    public ReviewMerchandiseResponseDTO leaveMerchandiseReview(int id, ReviewMerchandiseRequestDTO request) {
        User reviewer = userRepository.findById(request.getReviewerId()).orElseThrow(() -> new RuntimeException("User not found"));
        Review review = new Review();

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setReviewer(reviewer);
        review.setStatus(ReviewStatus.PENDING);
        review.setDeleted(false);
        review.setCreatedAt(LocalDateTime.now());
        Review savedReview = reviewRepository.save(review);

        if(request.getType().equals("merchandise")) {
            Merchandise reviewedMerchandise = merchandiseRepository.findById(id).orElseThrow(() -> new RuntimeException("Merchandise not found"));
            reviewedMerchandise.getReviews().add(review);
            merchandiseRepository.save(reviewedMerchandise);
        }else if(request.getType().equals("event")) {
            Event reviewedEvent = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
            reviewedEvent.getReviews().add(review);
            eventRepository.save(reviewedEvent);
        }else {
            throw new RuntimeException("Unsupported type");
        }

        return mapToReviewResponseDTO(savedReview);
    }

    public ReviewMerchandiseResponseDTO mapToReviewResponseDTO(Review review) {
        ReviewMerchandiseResponseDTO responseDTO = new ReviewMerchandiseResponseDTO();
        responseDTO.setId(review.getId());
        responseDTO.setComment(review.getComment());
        responseDTO.setRating(review.getRating());
        responseDTO.setReviewerId(review.getReviewer().getId());
        responseDTO.setStatus(review.getStatus());

        return responseDTO;
    }
}