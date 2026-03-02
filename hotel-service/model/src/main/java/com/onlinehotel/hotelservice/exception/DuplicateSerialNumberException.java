package com.onlinehotel.hotelservice.exception;

public class DuplicateSerialNumberException extends RuntimeException {
    public DuplicateSerialNumberException(String message) {
        super(message);
    }
    public DuplicateSerialNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
