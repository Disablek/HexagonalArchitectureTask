package com.onlinehotel.bookingservice.application.dto.event;

import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.DateRange;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

public record BookingCreatedEvent(
        Long eventId,
        Long bookingId,
        Long hotelId,
        Long hotelRoomId,
        DateRange dateRange,
        BigDecimal totalPrice,
        LocalDateTime createdAt
) {
    public static BookingCreatedEvent from(Booking booking) {
        return new BookingCreatedEvent(
                new Random().nextLong(),
                booking.getId(),
                booking.getHotelId(),
                booking.getHotelRoomId(),
                booking.getDateRange(),
                booking.getTotalPrice(),
                LocalDateTime.now()
        );
    }
}

