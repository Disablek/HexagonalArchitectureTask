package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;

public interface UpdateHotelRoomUseCase {
    HotelRoom execute(Long roomId) throws HotelRoomNotFoundException;
}
