package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.GetAllHotelRoomsUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.model.HotelRoom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class GetAllHotelRoomsService implements GetAllHotelRoomsUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    public GetAllHotelRoomsService(HotelRoomRepositoryPort hotelRoomRepositoryPort) {
        this.hotelRoomRepositoryPort = hotelRoomRepositoryPort;
    }

    @Override
    public Set<HotelRoom> execute() {
        return hotelRoomRepositoryPort.findAll();
    }
}
