package com.onlinehotel.hotelservice.application.port.out.persistence;

import com.onlinehotel.hotelservice.model.Hotel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelRepositoryPort {
    Mono<Hotel> findById(Long id);

    Mono<Hotel> save(Hotel hotel);

    Mono<Void> deleteById(Long id);

    Flux<Hotel> findAll();
}
