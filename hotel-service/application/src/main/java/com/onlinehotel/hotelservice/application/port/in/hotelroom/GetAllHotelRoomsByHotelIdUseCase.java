package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;

import java.util.Set;

public interface GetAllHotelRoomsByHotelIdUseCase {
    Set<HotelRoom> execute(Long hotelId) throws HotelNotFoundException;
}
