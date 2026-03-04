package com.onlinehotel.bookingservice.application.port.out.grpc;

import com.onlinehotel.bookingservice.application.dto.RoomInfo;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface HotelClientPort {
    boolean checkAvailability(
            Long hotelId,
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut
    );

    BigDecimal getRoomPrice(Long hotelId, Long roomId);

    RoomInfo getRoomInfo(Long hotelId, Long roomId);
}
