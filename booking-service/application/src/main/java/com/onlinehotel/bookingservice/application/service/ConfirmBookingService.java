package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.ConfirmBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.BookingStatus;
import org.springframework.stereotype.Service;

@Service
public class ConfirmBookingService implements ConfirmBookingUseCase {

    private final BookingRepositoryPort bookingRepositoryPort;

    public ConfirmBookingService(BookingRepositoryPort bookingRepositoryPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    @Override
    public void confirmBooking(Long bookingId) {
        Booking booking = bookingRepositoryPort.findById(bookingId);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        bookingRepositoryPort.save(booking);
    }
}
