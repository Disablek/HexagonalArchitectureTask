package com.onlinehotel.bookingservice.application.port.out.persistence;

import com.onlinehotel.bookingservice.model.Booking;

import java.util.Set;

public interface BookingRepositoryPort {
    Booking findById(Long id);

    Set<Booking> findAll();

    Booking save(Booking booking);
}
