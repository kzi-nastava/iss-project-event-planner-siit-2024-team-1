package com.example.eventplanner.dto.merchandise.review;

import lombok.Data;

@Data
public class ReviewMerchandiseRequestDTO {
    private int reviewerId;
    private String comment;
    private int rating;
    private String type;

}
