package com.onlinehotel.bookingservice.application.dto;

import java.math.BigDecimal;

public record RoomInfo (
        Long roomId,
        String type,
        String roomNumber,
        BigDecimal pricePerNight
) {}
