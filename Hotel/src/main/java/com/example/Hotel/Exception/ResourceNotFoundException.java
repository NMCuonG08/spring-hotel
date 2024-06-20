package com.example.Hotel.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public   ResourceNotFoundException(String message) {
        super(message);
    }
}
