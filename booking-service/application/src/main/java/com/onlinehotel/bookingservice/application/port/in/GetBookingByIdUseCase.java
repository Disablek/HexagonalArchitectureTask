package com.onlinehotel.bookingservice.application.port.in;

import com.onlinehotel.bookingservice.model.Booking;

public interface GetBookingByIdUseCase {
    Booking execute(Long bookingId);
}
