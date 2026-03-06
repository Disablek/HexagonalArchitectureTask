package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.GetAllHotelRoomsByHotelIdUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class GetAllHotelRoomsByHotelIdService implements GetAllHotelRoomsByHotelIdUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    public GetAllHotelRoomsByHotelIdService(HotelRoomRepositoryPort hotelRoomRepositoryPort) {
        this.hotelRoomRepositoryPort = hotelRoomRepositoryPort;
    }

    @Override
    @Cacheable(value = "hotelRoomsByHotelId", key = "#hotelId")
    public Set<HotelRoom> execute(Long hotelId) throws HotelNotFoundException {
        return hotelRoomRepositoryPort.findAllByHotelId(hotelId);
    }
}
