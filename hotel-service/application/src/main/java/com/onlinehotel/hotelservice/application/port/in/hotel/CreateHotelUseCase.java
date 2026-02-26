package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;

public interface CreateHotelUseCase {
    Hotel execute(CreateHotelCommand command) throws HotelNotFoundException;

    /// Command object for cleaner input
    record CreateHotelCommand(
            String hotelName,
            String hotelAddress) {
    }
}
