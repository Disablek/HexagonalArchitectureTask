package com.onlinehotel.bookingservice.application.port.in;

import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.DateRange;

import java.util.Optional;

public interface UpdateBookingUseCase {
    Booking execute(Long id, UpdateBookingCommand command);

    record UpdateBookingCommand(
            Optional<Long> hotelRoomId,
            Optional<DateRange> dateRange
    ){}
}
