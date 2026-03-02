package com.onlinehotel.hotelservice.application.port.out.persistence;

import com.onlinehotel.hotelservice.model.Hotel;

import java.util.Set;

public interface HotelRepositoryPort {
    Hotel findById(Long id);

    Hotel save(Hotel hotel);

    void deleteById(Long id);

    Set<Hotel> findAll();
}
