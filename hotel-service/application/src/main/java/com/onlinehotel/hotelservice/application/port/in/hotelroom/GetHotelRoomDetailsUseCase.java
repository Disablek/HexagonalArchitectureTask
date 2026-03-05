package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.application.dto.HotelRoomDetailsDto;

public interface GetHotelRoomDetailsUseCase {
   HotelRoomDetailsDto details(Long roomId);
}
