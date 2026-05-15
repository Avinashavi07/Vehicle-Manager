package com.exaact.vehiclemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standardized API response wrapper.
 * All API responses follow this structure for consistency.
 *
 * @param <T> the type of the response data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    /**
     * Indicates if the request was successful.
     */
    private boolean success;

    /**
     * Human-readable message (internationalized).
     */
    private String message;

    /**
     * Message code for client-side handling.
     */
    private String messageCode;

    /**
     * Response data payload.
     */
    private T data;

    /**
     * Validation errors (only for validation failures).
     */
    private List<ErrorDetail> errors;

    /**
     * Trace ID for error tracking and debugging.
     */
    private String traceId;

    /**
     * Response timestamp.
     */
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Create a successful response with data.
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    /**
     * Create a successful response with data and message.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Create a successful response with data, message, and message code.
     */
    public static <T> ApiResponse<T> success(T data, String message, String messageCode) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .messageCode(messageCode)
                .data(data)
                .build();
    }

    /**
     * Create a successful response with message only (no data).
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    /**
     * Create an error response.
     */
    public static <T> ApiResponse<T> error(String message, String messageCode, String traceId) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .messageCode(messageCode)
                .traceId(traceId)
                .build();
    }

    /**
     * Create an error response with data.
     */
    public static <T> ApiResponse<T> error(String message, String messageCode, String traceId, T data) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .messageCode(messageCode)
                .traceId(traceId)
                .data(data)
                .build();
    }

    /**
     * Create a validation error response.
     */
    public static <T> ApiResponse<T> validationError(String message, List<ErrorDetail> errors, String traceId) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .messageCode("validation.error")
                .errors(errors)
                .traceId(traceId)
                .build();
    }

    /**
     * Create a created response (HTTP 201).
     */
    public static <T> ApiResponse<T> created(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .messageCode("common.created")
                .data(data)
                .build();
    }

    /**
     * Create an accepted response (HTTP 202).
     */
    public static <T> ApiResponse<T> accepted(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .messageCode("common.accepted")
                .build();
    }
}

