package com.onlinehotel.hotelservice.exception;

public class HotelRoomNotFoundException extends RuntimeException {
    public HotelRoomNotFoundException(String message) {
        super(message);
    }
    public HotelRoomNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
