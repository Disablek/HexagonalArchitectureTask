package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.GetHotelRoomByIdUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetHotelRoomByIdService implements GetHotelRoomByIdUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    public GetHotelRoomByIdService(HotelRoomRepositoryPort hotelRoomRepositoryPort) {
        this.hotelRoomRepositoryPort = hotelRoomRepositoryPort;
    }

    @Override
    @Cacheable(value = "hotelRoomCache", key = "#roomId")
    public HotelRoom execute(Long roomId) throws HotelRoomNotFoundException {
        return hotelRoomRepositoryPort.findById(roomId);
    }
}
