package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import reactor.core.publisher.Mono;

public interface DeleteHotelRoomUseCase {
    Mono<Void> execute(Long roomId) throws HotelRoomNotFoundException;
}
