package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.CancelBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.BookingStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CancelBookingService implements CancelBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;

    public CancelBookingService(BookingRepositoryPort bookingRepositoryPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    @Override
    public void execute(Long bookingId) {
        Booking booking = bookingRepositoryPort.findById(bookingId);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepositoryPort.save(booking);
    }
}
