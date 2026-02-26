package com.onlinehotel.hotelservice.exception;

public class HotelAlreadyExists extends RuntimeException {
    public HotelAlreadyExists(String message) {
        super(message);
    }

    public HotelAlreadyExists(String message,  Throwable cause) {
        super(message, cause);
    }
}
