package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateBookingService implements CreateBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;

    public CreateBookingService(BookingRepositoryPort bookingRepositoryPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    @Override
    public Booking execute(Booking booking) {
        return bookingRepositoryPort.save(booking);
    }
}
