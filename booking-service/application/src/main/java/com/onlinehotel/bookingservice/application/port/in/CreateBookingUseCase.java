package com.onlinehotel.bookingservice.application.port.in;

import com.onlinehotel.bookingservice.model.Booking;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CreateBookingUseCase {
    Mono<Booking> execute(Booking booking);

    record CreateBookingCommand(
        Long hotelId,
        Long hotelRoomId,
        LocalDate checkIn,
        LocalDate checkOut
    ){}
}
