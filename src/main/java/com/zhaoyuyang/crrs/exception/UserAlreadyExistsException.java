package com.zhaoyuyang.crrs.exception;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException() {
        super("User already exists.");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
