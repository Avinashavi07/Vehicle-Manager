package com.exaact.vehiclemanager.exception;

import com.exaact.vehiclemanager.dto.ApiResponse;
import com.exaact.vehiclemanager.dto.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Business exceptions
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleBusinessException(
            BusinessException ex
    ) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }

        if (ex instanceof UnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
        }

        if (ex instanceof DuplicateResourceException) {
            status = HttpStatus.CONFLICT;
        }

        ApiResponse<Object> response =
                ApiResponse.error(
                        ex.getMessage(),
                        ex.getMessageCode(),
                        generateTraceId()
                );

        return ResponseEntity
                .status(status)
                .body(response);
    }

    /**
     * Validation exceptions
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>>
    handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        List<ErrorDetail> errors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                ErrorDetail.of(
                                        error.getField(),
                                        "VALIDATION_ERROR",
                                        error.getDefaultMessage(),
                                        error.getRejectedValue()
                                )
                        )
                        .toList();

        ApiResponse<Object> response =
                ApiResponse.validationError(
                        "Validation failed",
                        errors,
                        generateTraceId()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    /**
     * Catch-all exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>>
    handleException(Exception ex) {

        ex.printStackTrace();

        ApiResponse<Object> response =
                ApiResponse.error(
                        ex.getMessage(),
                        "INTERNAL_SERVER_ERROR",
                        generateTraceId()
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    /**
     * Generate trace ID
     */
    private String generateTraceId() {
        return UUID.randomUUID().toString();
    }
}