package com.onlinehotel.bookingservice.application.port.in;

import com.onlinehotel.bookingservice.model.Booking;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

public interface UpdateBookingUseCase {
    Mono<Booking> execute(Long id, UpdateBookingCommand command);

    record UpdateBookingCommand(
            Optional<Long> hotelRoomId,
            Optional<LocalDate> checkIn,
            Optional<LocalDate> checkOut
    ){}
}
