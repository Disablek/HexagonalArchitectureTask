package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.exception.InvalidArgumentException;
import com.onlinehotel.hotelservice.model.Hotel;

import java.util.Optional;

public interface GetHotelUseCase {
    Optional<Hotel> execute(Long id) throws HotelNotFoundException;
}
