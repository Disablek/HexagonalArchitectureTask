package com.onlinehotel.bookingservice.application.port.in;

public interface CancelBookingUseCase {
    void execute(Long bookingId);
}
