package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.GetBookingByIdUseCase;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetBookingByIdService implements GetBookingByIdUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;

    public GetBookingByIdService(BookingRepositoryPort bookingRepositoryPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    @Override
    public Booking execute(Long bookingId) {
        return bookingRepositoryPort.findById(bookingId);
    }
}
