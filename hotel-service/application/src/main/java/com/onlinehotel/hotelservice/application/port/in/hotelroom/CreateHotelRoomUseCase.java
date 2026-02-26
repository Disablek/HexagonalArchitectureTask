package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomAlreadyExists;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.model.RoomType;

import java.math.BigDecimal;

public interface CreateHotelRoomUseCase {
    HotelRoom execute(CreateHotelRoomCommand command) throws HotelRoomAlreadyExists;

    record CreateHotelRoomCommand(
            Integer serialNumber,
            Integer capacity,
            Long hotelId,
            BigDecimal price,
            RoomType roomType
    ){}
}
