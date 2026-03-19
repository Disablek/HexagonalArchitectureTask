package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomAlreadyExists;
import com.onlinehotel.hotelservice.model.HotelRoom;
import reactor.core.publisher.Mono;

public interface CreateHotelRoomUseCase {
    Mono<HotelRoom> execute(Long id, HotelRoom hotelRoom) throws HotelRoomAlreadyExists;

}
