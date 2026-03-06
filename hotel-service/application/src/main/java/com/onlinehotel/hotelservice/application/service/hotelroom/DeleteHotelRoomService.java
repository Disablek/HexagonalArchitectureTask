package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.DeleteHotelRoomUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteHotelRoomService implements DeleteHotelRoomUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    public DeleteHotelRoomService(HotelRoomRepositoryPort hotelRoomRepositoryPort) {
        this.hotelRoomRepositoryPort = hotelRoomRepositoryPort;
    }

    @Override
    public void execute(Long roomId) throws HotelRoomNotFoundException {
        hotelRoomRepositoryPort.findById(roomId);
        hotelRoomRepositoryPort.deleteById(roomId);
    }
}
