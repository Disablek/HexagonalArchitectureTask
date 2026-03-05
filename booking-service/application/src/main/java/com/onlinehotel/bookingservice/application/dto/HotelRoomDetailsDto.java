package com.onlinehotel.bookingservice.application.dto;

import java.math.BigDecimal;

public record HotelRoomDetailsDto(
        Long roomId,
        String roomType,
        Integer capacity,
        Integer serialNumber,
        BigDecimal price
) {}
