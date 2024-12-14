package com.example.eventplanner.dto.merchandise.review;

import com.example.eventplanner.model.merchandise.ReviewStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOverviewDTO {
    private int id;

    private String comment;
    private int rating;
    private ReviewStatus status;
    private String reviewedType;
    private String reviewedTitle;
    private String reviewerUsername;
    private LocalDateTime createdAt;
}
