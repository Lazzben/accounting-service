package com.github.lazyben.accounting.exception;

import org.springframework.http.HttpStatus;

public class InvalidParameterException extends ServiceException {
    public InvalidParameterException(String message) {
        super(message);
        this.setBizErrorCode(BizErrorCode.INVALID_PARAMETER);
        this.setStatusCode(HttpStatus.BAD_REQUEST.value());
    }
}
