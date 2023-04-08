package com.randy.propertymanagementsystem.exception;

public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException(String s) {
        super(s);
    }
}
