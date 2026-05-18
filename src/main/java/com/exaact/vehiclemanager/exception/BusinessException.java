package com.exaact.vehiclemanager.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final String messageCode;

    public BusinessException(
            String message,
            String messageCode
    ) {
        super(message);
        this.messageCode = messageCode;
    }
}