package com.exaact.vehiclemanager.exception;

public class ResourceNotFoundException
        extends BusinessException {

    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND");
    }
}