package com.example.eventplanner.controllers.review;

import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseRequestDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewMerchandiseResponseDTO;
import com.example.eventplanner.dto.merchandise.review.ReviewOverviewDTO;
import com.example.eventplanner.dto.review.ReviewDTO;
import com.example.eventplanner.dto.review.ReviewUpdateResponseDTO;
import com.example.eventplanner.services.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PutMapping("/{id}/approve")
    public ResponseEntity<ReviewOverviewDTO> approveReview(@PathVariable int id) {
        ReviewOverviewDTO approvedReview = reviewService.approveReview(id);
        return ResponseEntity.ok(approvedReview);
    }

    @PutMapping("/{id}/deny")
    public ResponseEntity<ReviewOverviewDTO> denyReview(@PathVariable int id) {
        ReviewOverviewDTO deniedReview = reviewService.denyReview(id);
        return ResponseEntity.ok(deniedReview);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ReviewOverviewDTO>> getAllPendingReviews() {
        List<ReviewOverviewDTO> pendingReviews = reviewService.getAllPendingReviews();
        return ResponseEntity.ok(pendingReviews);
    }

    @PostMapping("/{id}/add")
    public ResponseEntity<ReviewMerchandiseResponseDTO> leaveMerchandiseReview(@PathVariable int id,
                                                                               @RequestBody ReviewMerchandiseRequestDTO request) {
        ReviewMerchandiseResponseDTO responseDTO = reviewService.leaveMerchandiseReview(id, request);
        return ResponseEntity.ok(new ReviewMerchandiseResponseDTO());
    }
}
