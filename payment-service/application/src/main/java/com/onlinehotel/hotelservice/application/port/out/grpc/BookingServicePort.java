package com.onlinehotel.hotelservice.application.port.out.grpc;

import com.onlinehotel.hotelservice.application.dto.BookingDetailsDto;

public interface BookingServicePort {
    BookingDetailsDto getBookingDetails(Long bookingId);
}
