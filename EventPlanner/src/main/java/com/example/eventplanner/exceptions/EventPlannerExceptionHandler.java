package com.example.eventplanner.exceptions;

import com.example.eventplanner.dto.user.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class EventPlannerExceptionHandler {

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAuthenticationException(UserAuthenticationException ex) {
        HttpStatus status;
        switch (ex.getErrorType()) {
            case  INVALID_CREDENTIALS -> status = HttpStatus.FORBIDDEN;
            case USER_SUSPENDED -> status = HttpStatus.FORBIDDEN;
            case ACCOUNT_NOT_VERIFIED,USER_NOT_FOUND -> status = HttpStatus.BAD_REQUEST;
            default -> status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), ex.getErrorType().name()), status);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponseDto> handleTokenExpiredException(TokenExpiredException ex) {
        return new ResponseEntity<>(
                new ErrorResponseDto(ex.getMessage(), "TOKEN_EXPIRED"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(BlockedMerchandiseException.class)
    public ResponseEntity<ErrorResponseDto> handleBlockedMerchandise(BlockedMerchandiseException ex) {

        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), "blocked user"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleBlockedMerchandise(EntityNotFoundException ex) {

        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), "SERVICE_NOT_FOUND"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegisterUserException.class)
    public ResponseEntity<ErrorResponseDto> handleRegisterUser(RegisterUserException ex) {
        HttpStatus status;
        switch (ex.getErrorType()) {
            case USER_ALREADY_EXISTS -> status = HttpStatus.BAD_REQUEST;
            default -> status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), ex.getErrorType().name()), status);
    }

    @ExceptionHandler(ServiceReservationException.class)
    public ResponseEntity<ErrorResponseDto> handleServiceReservationException(ServiceReservationException e) {
        HttpStatus status;
        switch (e.getErrorType()) {
            case SERVICE_NOT_FOUND:
            case EVENT_NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                break;
            case TIMING_CONSTRAINT_VIOLATION:
            case DURATION_CONSTRAINT_VIOLATION:
            case TIME_SLOT_ALREADY_BOOKED:
                status = HttpStatus.BAD_REQUEST;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(
                new ErrorResponseDto(e.getMessage(), e.getErrorType().name()),
                status
        );
    }

    @ExceptionHandler(EventException.class)
    public ResponseEntity<ErrorResponseDto> handleEventException(EventException e) {
        HttpStatus status;
        switch (e.getErrorType()) {
            case EVENT_NOT_FOUND:
            case ORGANIZER_NOT_FOUND:
            case EVENT_TYPE_NOT_FOUND:
            case ACTIVITY_NOT_FOUND:
                status = HttpStatus.NOT_FOUND;
                break;
            case INVALID_ACTIVITY_TIME:
            case INVALID_DATE:
                status = HttpStatus.BAD_REQUEST;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(
                new ErrorResponseDto(e.getMessage(), e.getErrorType().name()),
                status
        );
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorResponseDto> handleProductException(ProductException e) {
        HttpStatus status;
        switch (e.getErrorType()) {
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(
                new ErrorResponseDto(e.getMessage(), e.getErrorType().name()),
                status
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return new ResponseEntity<>(
                new ErrorResponseDto(message, "VALIDATION_ERROR"),
                HttpStatus.BAD_REQUEST
        );
    }
}