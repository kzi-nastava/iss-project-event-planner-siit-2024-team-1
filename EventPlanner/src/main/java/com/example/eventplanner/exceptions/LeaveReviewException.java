package com.example.eventplanner.exceptions;

public class LeaveReviewException extends RuntimeException {
    public enum ErrorType {
        USER_NOT_FOUND,
        EVENT_NOT_FOUND,
        MERCHANDISE_NOT_FOUND,
        REVIEW_ALREADY_EXISTS,
        UNSUPORTED_TYPE
    }
    private ErrorType errorType;

    public LeaveReviewException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
