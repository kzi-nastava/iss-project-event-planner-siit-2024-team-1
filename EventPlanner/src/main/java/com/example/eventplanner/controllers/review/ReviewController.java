package com.example.eventplanner.controllers.review;

import com.example.eventplanner.dto.review.ReviewDTO;
import com.example.eventplanner.dto.review.ReviewUpdateResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @PutMapping("/{id}")
    public ResponseEntity<ReviewUpdateResponseDTO> updateReview(@PathVariable int id, @RequestBody ReviewDTO reviewDetails) {
        return ResponseEntity.ok(new ReviewUpdateResponseDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable int id) {
        return ResponseEntity.noContent().build();
    }
}
