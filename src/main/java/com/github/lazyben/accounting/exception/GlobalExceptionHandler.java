package com.github.lazyben.accounting.exception;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handelException(ServiceException e) {
        return ResponseEntity.status(e.getStatusCode())
                .body(ErrorResponse.builder()
                        .bizErrorCode(e.getBizErrorCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<?> handelException(IncorrectCredentialsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(ErrorResponse.builder()
                        .bizErrorCode(BizErrorCode.INCORRECT_PASSWORD)
                        .message(e.getMessage())
                        .build());
    }
}
