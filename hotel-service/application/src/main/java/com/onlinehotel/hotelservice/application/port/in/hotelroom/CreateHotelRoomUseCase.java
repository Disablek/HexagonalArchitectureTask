package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomAlreadyExists;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.model.RoomType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public interface CreateHotelRoomUseCase {
    HotelRoom execute(Long id,HotelRoom hotelRoom) throws HotelRoomAlreadyExists;

}
