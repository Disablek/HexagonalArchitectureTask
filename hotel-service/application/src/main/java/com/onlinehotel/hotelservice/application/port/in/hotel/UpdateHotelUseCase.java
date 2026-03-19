package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.model.HotelRoom;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

public interface UpdateHotelUseCase {
    Mono<Hotel> execute(Long id , UpdateHotelCommand command) throws HotelNotFoundException;

    record UpdateHotelCommand(
            Optional<String> name,
            Optional<String> address) {
    }
}
