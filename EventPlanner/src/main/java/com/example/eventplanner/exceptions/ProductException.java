package com.example.eventplanner.exceptions;

public class ProductException extends RuntimeException {
    private final ProductException.ErrorType errorType;

    public enum ErrorType {

    }

    public ProductException(String message, ProductException.ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ProductException.ErrorType getErrorType() {
        return errorType;
    }
}