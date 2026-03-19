package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.application.dto.HotelRoomDetailsDto;
import reactor.core.publisher.Mono;

public interface GetHotelRoomDetailsUseCase {
   Mono<HotelRoomDetailsDto> details(Long roomId);
}
