package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.GetBookingByIdUseCase;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetBookingByIdService implements GetBookingByIdUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;

    public GetBookingByIdService(BookingRepositoryPort bookingRepositoryPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    @Override
    @Transactional
    @Cacheable(value = "bookingCache", key = "#result.id")
    public Booking execute(Long bookingId) {
        return bookingRepositoryPort.findById(bookingId);
    }
}
