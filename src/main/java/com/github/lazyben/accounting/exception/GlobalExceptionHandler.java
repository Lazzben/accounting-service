package com.github.lazyben.accounting.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handelException(ServiceException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(ErrorResponse.builder()
                        .BizErrorCode(e.getBizErrorCode())
                        .message(e.getMessage())
                        .build());
    }
}
