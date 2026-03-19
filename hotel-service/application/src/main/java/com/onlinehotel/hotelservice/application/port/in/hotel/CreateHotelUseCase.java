package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import reactor.core.publisher.Mono;

public interface CreateHotelUseCase {
    Mono<Hotel> execute(Hotel hotel) throws HotelNotFoundException;

    /// Command object for cleaner input
    record CreateHotelCommand(
            String hotelName,
            String hotelAddress) {
    }
}
