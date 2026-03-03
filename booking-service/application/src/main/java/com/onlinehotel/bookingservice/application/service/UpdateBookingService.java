package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.UpdateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateBookingService implements UpdateBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;

    public UpdateBookingService(BookingRepositoryPort bookingRepositoryPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
    }

    @Override
    public Booking execute(Long id, UpdateBookingCommand command) {
        Booking booking = bookingRepositoryPort.findById(id);
        updatePartially(booking, command);
        return bookingRepositoryPort.save(booking);
    }

    void updatePartially(Booking booking, UpdateBookingCommand command) {
        if (command.hotelRoomId().isPresent())
            booking.setHotelRoomId(command.hotelRoomId().get());
        if (command.dateRange().isPresent())
            booking.setDateRange(command.dateRange().get());
    }
}
