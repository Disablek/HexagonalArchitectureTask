package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.CreateHotelRoomUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.DuplicateSerialNumberException;
import com.onlinehotel.hotelservice.exception.HotelRoomAlreadyExists;
import com.onlinehotel.hotelservice.model.HotelRoom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateHotelRoomService implements CreateHotelRoomUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    public CreateHotelRoomService(HotelRoomRepositoryPort hotelRoomRepositoryPort) {
        this.hotelRoomRepositoryPort = hotelRoomRepositoryPort;
    }

    @Override
    public HotelRoom execute(Long id,HotelRoom hotelRoom) throws DuplicateSerialNumberException, HotelRoomAlreadyExists {
        boolean serialExists = hotelRoomRepositoryPort
                .findAllByHotelId(id)
                .stream()
                .anyMatch(room -> room.getSerialNumber().equals(hotelRoom.getSerialNumber()));

        if (serialExists) {
            throw new DuplicateSerialNumberException(
                    "SerialNumber " + hotelRoom.getSerialNumber() +
                            " already exists in hotel " + id);
        }
        hotelRoom.setHotelId(id);
        return hotelRoomRepositoryPort.save(hotelRoom);
    }
}
