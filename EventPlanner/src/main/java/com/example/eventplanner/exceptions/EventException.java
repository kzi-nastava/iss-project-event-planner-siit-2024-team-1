package com.example.eventplanner.exceptions;

public class EventException extends RuntimeException {
    private final EventException.ErrorType errorType;

    public enum ErrorType {
        EVENT_NOT_FOUND,
        ORGANIZER_NOT_FOUND,
        INVALID_DATE,
        EVENT_TYPE_NOT_FOUND,
        ACTIVITY_NOT_FOUND,
        INVALID_ACTIVITY_TIME
    }

    public EventException(String message, EventException.ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public EventException.ErrorType getErrorType() {
        return errorType;
    }
}