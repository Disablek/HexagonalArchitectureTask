package com.onlinehotel.bookingservice.application.port.out.grpc;

import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface HotelRoomServicePort {
    boolean checkAvailability(
            Long hotelId,
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut
    );

    Mono<HotelRoomDetailsDto> getHotelDetails(Long hotelId, Long roomId);
}
