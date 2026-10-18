package com.zhaoyuyang.crrs.exception;

public class BusinessException extends RuntimeException {
    public BusinessException() {
        super("Business exception.");
    }

    public BusinessException(String message) {
        super(message);
    }
}
