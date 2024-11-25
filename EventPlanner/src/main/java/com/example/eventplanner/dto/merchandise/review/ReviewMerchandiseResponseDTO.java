package com.example.eventplanner.dto.merchandise.review;

import lombok.Data;

@Data
public class ReviewMerchandiseResponseDTO {
    private int id;
    private String comment;
    private int rating;
    private boolean status;
}
