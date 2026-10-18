package com.zhaoyuyang.crrs.exception;

public class PasswordIncorrectException extends BusinessException {
    public PasswordIncorrectException() {
        super("Password incorrect.");
    }
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
