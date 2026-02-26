package com.onlinehotel.hotelservice.exception;

public class HotelRoomAlreadyExists extends RuntimeException {
    public HotelRoomAlreadyExists(String message) {
        super(message);
    }

    public HotelRoomAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }
}
