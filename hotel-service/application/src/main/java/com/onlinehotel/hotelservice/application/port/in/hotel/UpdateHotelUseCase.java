package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.model.HotelRoom;

import java.util.Optional;
import java.util.Set;

public interface UpdateHotelUseCase {
    Hotel execute(UpdateHotelCommand command) throws HotelNotFoundException;

    record UpdateHotelCommand(
            Long hotelId,
            Optional<String> name,
            Optional<String> address,
            Optional<Set<HotelRoom>> hotelRoom) {
    }
}
