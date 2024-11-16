package com.example.eventplanner.dto.event;

import com.example.eventplanner.model.merchandise.Review;
import com.example.eventplanner.model.user.User;

import java.util.List;

public class GetEventReportResponseDTO {
    private List<User> visitors;
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<User> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<User> visitors) {
        this.visitors = visitors;
    }
}
