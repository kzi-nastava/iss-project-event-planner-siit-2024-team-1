package com.example.eventplanner.dto.merchandise.review;

import com.example.eventplanner.model.merchandise.ReviewStatus;
import lombok.Data;

@Data
public class ReviewMerchandiseResponseDTO {
    private int id;
    private String comment;
    private int rating;
    private int reviewerId;
    private ReviewStatus status;
}
