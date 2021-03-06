package com.github.lazyben.accounting.exception;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException {
    private BizErrorCode BizErrorCode;
    private int statusCode;

    public ServiceException(String message) {
        super(message);
    }
}
