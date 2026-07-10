package com.logden.backend.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // 404 Not Found
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.NOT_FOUND.value(),
                                exception.getMessage(),
                                LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // 500 Internal Server Error
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleException(Exception exception) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Unexpected error occurred",
                                LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(error);
        }

        // 400 Bad Request
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {

                FieldError fieldError = exception.getBindingResult().getFieldError();
                String message = fieldError.getField() + ": " + fieldError.getDefaultMessage();

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                message,
                                LocalDateTime.now());

                return ResponseEntity.badRequest().body(error);
        }

        // 400 Bad Request
        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ErrorResponse> handleIllegalStateException(
                        IllegalStateException exception) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.BAD_REQUEST.value(),
                                exception.getMessage(),
                                LocalDateTime.now());

                return ResponseEntity.badRequest().body(error);
        }

        // 409 Conflict
        @ExceptionHandler(ResourceAlreadyExistsException.class)
        public ResponseEntity<ErrorResponse> handleResourceAlreadyExists(ResourceAlreadyExistsException exception) {

                ErrorResponse error = new ErrorResponse(
                                HttpStatus.CONFLICT.value(),
                                exception.getMessage(),
                                LocalDateTime.now());

                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
}
