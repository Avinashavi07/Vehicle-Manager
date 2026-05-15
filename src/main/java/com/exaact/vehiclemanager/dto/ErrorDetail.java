package com.exaact.vehiclemanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Details of a validation or field-specific error.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {

    /**
     * The field that has the error (if applicable).
     */
    private String field;

    /**
     * Error code for client-side handling.
     */
    private String code;

    /**
     * Human-readable error message (internationalized).
     */
    private String message;

    /**
     * The rejected value (if applicable).
     */
    private Object rejectedValue;

    /**
     * Create a simple error detail.
     */
    public static ErrorDetail of(String field, String code, String message) {
        return ErrorDetail.builder()
                .field(field)
                .code(code)
                .message(message)
                .build();
    }

    /**
     * Create an error detail with rejected value.
     */
    public static ErrorDetail of(String field, String code, String message, Object rejectedValue) {
        return ErrorDetail.builder()
                .field(field)
                .code(code)
                .message(message)
                .rejectedValue(rejectedValue)
                .build();
    }
}

