package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;

public interface DeleteHotelUseCase {
    void execute(Long id) throws HotelNotFoundException;
}
