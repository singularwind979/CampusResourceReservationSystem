package com.zhaoyuyang.crrs.exception;

public class ParamEmptyException extends BusinessException {
    public ParamEmptyException() {
        super("Param empty.");
    }

    public ParamEmptyException(String message) {
        super(message);
    }
}
