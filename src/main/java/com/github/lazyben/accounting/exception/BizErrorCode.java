package com.github.lazyben.accounting.exception;

public enum BizErrorCode {
    INVALID_PARAMETER("INVALID_PARAMETER"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND"),
    INCORRECT_PASSWORD("INCORRECT_PASSWORD"),
    UN_AUTHORIZED("UN_AUTHORIZED");

    private final String msg;

    BizErrorCode(String msg) {
        this.msg = msg;
    }
}
