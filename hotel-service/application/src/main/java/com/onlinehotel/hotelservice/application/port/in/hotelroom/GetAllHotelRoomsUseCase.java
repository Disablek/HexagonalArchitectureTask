package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.model.HotelRoom;

import java.util.Set;

public interface GetAllHotelRoomsUseCase {
    Set<HotelRoom> execute();
}
