package com.example.eventplanner.exceptions;

import com.example.eventplanner.dto.user.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAuthenticationException(UserAuthenticationException ex) {
        HttpStatus status;
        switch (ex.getErrorType()) {
            case USER_NOT_FOUND, INVALID_CREDENTIALS -> status = HttpStatus.UNAUTHORIZED;
            case USER_SUSPENDED -> status = HttpStatus.FORBIDDEN;
            case ACCOUNT_NOT_VERIFIED -> status = HttpStatus.BAD_REQUEST;
            default -> status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(new ErrorResponseDto(ex.getMessage(), ex.getErrorType().name()), status);
    }
}