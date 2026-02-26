package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;

public interface DeleteHotelRoomUseCase {
    void execute(Long roomId) throws HotelRoomNotFoundException;
}
