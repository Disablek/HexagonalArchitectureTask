package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import reactor.core.publisher.Mono;

public interface DeleteHotelUseCase {
    Mono<Void> execute(Long id) throws HotelNotFoundException;
}
