package com.onlinehotel.bookingservice.application.dto.event;

import com.onlinehotel.bookingservice.model.Booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public record BookingCreatedEvent(
        Long eventId,
        Long bookingId,
        Long hotelId,
        Long hotelRoomId,
        LocalDate checkIn,
        LocalDate checkOut,
        BigDecimal totalPrice,
        LocalDateTime createdAt
) {
    public static BookingCreatedEvent from(Booking booking) {
        return new BookingCreatedEvent(
                new Random().nextLong(),
                booking.getId(),
                booking.getHotelId(),
                booking.getHotelRoomId(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getTotalPrice(),
                LocalDateTime.now()
        );
    }

    public static Booking to(BookingCreatedEvent bookingCreatedEvent) {
        return new Booking(
                null,
                bookingCreatedEvent.hotelId(),
                bookingCreatedEvent.hotelRoomId(),
                bookingCreatedEvent.checkIn(),
                bookingCreatedEvent.checkOut(),
                null,
                bookingCreatedEvent.totalPrice()
        );
    }
}

