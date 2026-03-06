package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.hotelservice.application.port.in.hotelroom.GetHotelRoomDetailsUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class GetHotelRoomDetailsService implements GetHotelRoomDetailsUseCase {

    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    @Override
    public HotelRoomDetailsDto details(Long roomId) {
        HotelRoom hotelRoom = hotelRoomRepositoryPort.findById(roomId);
        return HotelRoomDetailsDto.builder()
                .price(hotelRoom.getPrice())
                .serialNumber(hotelRoom.getSerialNumber())
                .roomType(hotelRoom.getRoomType())
                .capacity(hotelRoom.getCapacity())
                .build();
    }
}
