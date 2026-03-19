package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import reactor.core.publisher.Mono;

public interface GetHotelRoomByIdUseCase {
    Mono<HotelRoom> execute(Long roomId) throws HotelRoomNotFoundException;
}
