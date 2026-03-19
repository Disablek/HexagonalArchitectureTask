package com.onlinehotel.hotelservice.application.port.out.persistence;

import com.onlinehotel.hotelservice.model.HotelRoom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface HotelRoomRepositoryPort {
    Mono<HotelRoom> findById(Long id);

    Mono<HotelRoom> save(HotelRoom hotel);

    Mono<Void> deleteById(Long id);

    Flux<HotelRoom> findAllByHotelId(Long hotelId);

    Flux<HotelRoom> findAll();

    Mono<Boolean> existsByHotelIdAndSerialNumber(Long hotelId, Integer serialNumber);
}
