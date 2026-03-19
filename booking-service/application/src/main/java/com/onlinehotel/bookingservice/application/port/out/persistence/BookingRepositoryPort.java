package com.onlinehotel.bookingservice.application.port.out.persistence;

import com.onlinehotel.bookingservice.model.Booking;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingRepositoryPort {
    Mono<Booking> findById(Long id);

    Flux<Booking> findAll();

    Mono<Booking> save(Booking booking);
}
