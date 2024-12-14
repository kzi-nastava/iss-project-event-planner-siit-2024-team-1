package com.example.eventplanner.controllers.review;

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
    @PutMapping("/{id}")
    public ResponseEntity<ReviewUpdateResponseDTO> updateReview(@PathVariable int id, @RequestBody ReviewDTO reviewDetails) {
        return ResponseEntity.ok(new ReviewUpdateResponseDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable int id) {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ReviewOverviewDTO>> getAllPendingReviews() {
        List<ReviewOverviewDTO> pendingReviews = reviewService.getAllPendingReviews();
        return ResponseEntity.ok(pendingReviews);
    }

}
