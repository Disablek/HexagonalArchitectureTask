package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.UpdateHotelRoomUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateHotelRoomService implements UpdateHotelRoomUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    public UpdateHotelRoomService(HotelRoomRepositoryPort hotelRoomRepositoryPort) {
        this.hotelRoomRepositoryPort = hotelRoomRepositoryPort;
    }

    @Override
    @CachePut(value = "hotelRoomCache", key = "#result.id")
    public HotelRoom execute(Long id, UpdateHotelRoomCommand command) throws HotelRoomNotFoundException {
        HotelRoom hotelRoom = hotelRoomRepositoryPort.findById(id);
        updatePartially(hotelRoom, command);
        return hotelRoomRepositoryPort.save(hotelRoom);
    }

    void updatePartially(HotelRoom hotelRoom, UpdateHotelRoomCommand command) {
        if (command.capacity().isPresent())
            hotelRoom.setCapacity(command.capacity().get());
        if (command.serialNumber().isPresent())
            hotelRoom.setSerialNumber(command.serialNumber().get());
        if (command.roomType().isPresent())
            hotelRoom.setRoomType(command.roomType().get());
        if (command.price().isPresent())
            hotelRoom.setPrice(command.price().get());
    }
}
