package com.example.eventplanner.exceptions;

public class ServiceReservationException extends RuntimeException {
    private final ErrorType errorType;

    public enum ErrorType {
        SERVICE_NOT_FOUND,
        EVENT_NOT_FOUND,
        TIMING_CONSTRAINT_VIOLATION,
        DURATION_CONSTRAINT_VIOLATION,
        TIME_SLOT_ALREADY_BOOKED
    }

    public ServiceReservationException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}