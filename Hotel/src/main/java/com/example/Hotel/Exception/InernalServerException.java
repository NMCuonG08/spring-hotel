package com.example.Hotel.Exception;

import net.bytebuddy.implementation.bind.annotation.Super;

public class InernalServerException extends RuntimeException {
    public InernalServerException(String message) {
        super(message);
    }
}
