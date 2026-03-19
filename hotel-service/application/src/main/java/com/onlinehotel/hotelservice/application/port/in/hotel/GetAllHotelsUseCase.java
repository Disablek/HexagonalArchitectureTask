package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.model.Hotel;
import reactor.core.publisher.Flux;

public interface GetAllHotelsUseCase {
    Flux<Hotel> execute();
}
