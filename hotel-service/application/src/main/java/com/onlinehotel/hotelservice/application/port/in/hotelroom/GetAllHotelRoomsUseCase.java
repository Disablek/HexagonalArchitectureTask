package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.model.HotelRoom;
import reactor.core.publisher.Flux;

public interface GetAllHotelRoomsUseCase {
    Flux<HotelRoom> execute();
}
