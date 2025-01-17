package com.example.eventplanner.exceptions;

public class EventTypeException extends RuntimeException {
    private final EventTypeException.ErrorType errorType;

    public enum ErrorType {
        EVENT_TYPE_NOT_FOUND
    }

    public EventTypeException(String message, EventTypeException.ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public EventTypeException.ErrorType getErrorType() {
        return errorType;
    }
}
