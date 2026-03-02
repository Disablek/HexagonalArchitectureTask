package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;

import java.util.Optional;

public interface GetHotelRoomByIdUseCase {
    Optional<HotelRoom> execute(Long roomId) throws HotelRoomNotFoundException;
}
