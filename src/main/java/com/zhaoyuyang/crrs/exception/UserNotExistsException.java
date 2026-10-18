package com.zhaoyuyang.crrs.exception;

public class UserNotExistsException extends BusinessException {
    public UserNotExistsException() {
        super("User not exists.");
    }

    public UserNotExistsException(String message) {
        super(message);
    }
}
