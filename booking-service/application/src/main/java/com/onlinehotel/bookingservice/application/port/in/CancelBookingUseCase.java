package com.onlinehotel.bookingservice.application.port.in;

import com.onlinehotel.bookingservice.model.Booking;
import reactor.core.publisher.Mono;

public interface CancelBookingUseCase {
    Mono<Booking> execute(Long bookingId);
}
