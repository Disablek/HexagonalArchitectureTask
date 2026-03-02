package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.model.RoomType;

import java.math.BigDecimal;
import java.util.Optional;

public interface UpdateHotelRoomUseCase {
    HotelRoom execute(Long id, UpdateHotelRoomCommand command) throws HotelRoomNotFoundException;

    record UpdateHotelRoomCommand(
            Optional<Integer> serialNumber,
            Optional<Integer> capacity,
            Optional<BigDecimal> price,
            Optional<RoomType> roomType
    ){}
}
