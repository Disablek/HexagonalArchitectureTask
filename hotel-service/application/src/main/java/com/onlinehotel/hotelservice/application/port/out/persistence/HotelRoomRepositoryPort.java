package com.onlinehotel.hotelservice.application.port.out.persistence;

import com.onlinehotel.hotelservice.model.HotelRoom;

import java.util.Optional;
import java.util.Set;

public interface HotelRoomRepositoryPort {
    Optional<HotelRoom> findById(Long id);

    HotelRoom save(HotelRoom hotel);

    void deleteById(Long id);

    Set<HotelRoom> findAllByHotelId(Long hotelId);
}
