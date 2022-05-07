package com.github.lazyben.accounting.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ServiceException {
    public ResourceNotFoundException(String message) {
        super(message);
        this.setBizErrorCode("RESOURCE_NOT_FOUND");
        this.setStatusCode(HttpStatus.NOT_FOUND.value());
    }
}
