package com.onlinehotel.hotelservice.application.port.in.hotel;

import com.onlinehotel.hotelservice.model.Hotel;

import java.util.Set;

public interface GetAllHotelsUseCase {
    Set<Hotel> execute();
}
