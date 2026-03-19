package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import reactor.core.publisher.Flux;

public interface GetAllHotelRoomsByHotelIdUseCase {
    Flux<HotelRoom> execute(Long hotelId) throws HotelNotFoundException;
}
