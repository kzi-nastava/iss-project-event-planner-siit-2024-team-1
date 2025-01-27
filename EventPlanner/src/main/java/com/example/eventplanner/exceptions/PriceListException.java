package com.example.eventplanner.exceptions;

import lombok.Getter;

public class PriceListException extends RuntimeException {
    public enum ErrorType {
      SERVICE_PROVIDER_NOT_FOUND,
      MERCHANDISE_NOT_FOUND
    }

    @Getter
    private ErrorType errorType;

    public PriceListException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
}
