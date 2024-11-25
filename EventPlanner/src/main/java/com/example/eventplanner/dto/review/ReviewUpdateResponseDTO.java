package com.example.eventplanner.dto.review;

import lombok.Data;

@Data
public class ReviewUpdateResponseDTO {
    private int id;

    private String comment;
    private int rating;
    private boolean status;
}
