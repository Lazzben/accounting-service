package com.github.lazyben.accounting.exception;

import lombok.Data;

@Data
public class ServiceException extends Exception {
    private String BizErrorCode;
    private int statusCode;

    public ServiceException(String message) {
        super(message);
    }
}
