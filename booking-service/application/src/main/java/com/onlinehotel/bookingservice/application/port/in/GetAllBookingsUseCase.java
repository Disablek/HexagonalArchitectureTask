package com.onlinehotel.bookingservice.application.port.in;

import com.onlinehotel.bookingservice.model.Booking;
import reactor.core.publisher.Flux;

public interface GetAllBookingsUseCase {
    Flux<Booking> execute();
}
