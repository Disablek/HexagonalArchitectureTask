package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import reactor.core.publisher.Mono;

public interface GetHotelByIdUseCase {
    Mono<Hotel> execute(Long id) throws HotelNotFoundException;
}
