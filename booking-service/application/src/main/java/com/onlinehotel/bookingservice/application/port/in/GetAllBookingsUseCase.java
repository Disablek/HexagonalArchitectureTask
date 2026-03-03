package com.onlinehotel.bookingservice.application.port.in;

import com.onlinehotel.bookingservice.model.Booking;

import java.util.Set;

public interface GetAllBookingsUseCase {
    Set<Booking> execute();
}
