package com.onlinehotel.hotelservice.exception;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String message) {
        super(message);
    }
    public InvalidArgumentException(String message, String parameter) {
        super(message + " INVALID ARGUMENT: " + parameter);
    }
    public InvalidArgumentException(String message,  Throwable cause) {
        super(message, cause);
    }
}
