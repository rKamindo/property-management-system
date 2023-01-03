package com.example.tenantmanagementsystem.exception;

public class ApartmentNotFoundException extends RuntimeException {
    public ApartmentNotFoundException(String message) {
        super(message);
    }
}
