package com.example.eventplanner.exceptions;

import lombok.Getter;

public class CreateServiceException extends RuntimeException {
    public enum ErrorType {
        SERVICE_NOT_FOUND,
        SERVICE_ALREADY_EXISTS,
        CATEGORY_NOT_FOUND
    }

    @Getter
    private final ErrorType errorType;

    public CreateServiceException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
