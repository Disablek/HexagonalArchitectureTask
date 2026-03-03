package com.onlinehotel.bookingservice.application.port.in;

import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.DateRange;
public interface CreateBookingUseCase {
    Booking execute(Booking booking);

    record CreateBookingCommand(
        Long hotelId,
        Long hotelRoomId,
        DateRange dateRange
    ){}
}
