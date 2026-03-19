package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.UpdateHotelRoomUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UpdateHotelRoomService implements UpdateHotelRoomUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    public UpdateHotelRoomService(HotelRoomRepositoryPort hotelRoomRepositoryPort) {
        this.hotelRoomRepositoryPort = hotelRoomRepositoryPort;
    }

    @Override
    @CachePut(value = "hotelRoomCache", key = "#id")
    public Mono<HotelRoom> execute(Long id, UpdateHotelRoomCommand command) throws HotelRoomNotFoundException {
        return hotelRoomRepositoryPort.findById(id)
                .flatMap(s -> {
                    updatePartially(s, command);
                    return hotelRoomRepositoryPort.save(s);
                });
    }

    void updatePartially(HotelRoom hotelRoom, UpdateHotelRoomCommand command) {
        if (command.getCapacity() != null)
            hotelRoom.setCapacity(command.getCapacity());
        if (command.getSerialNumber() != null)
            hotelRoom.setSerialNumber(command.getSerialNumber());
        if (command.getRoomType() != null)
            hotelRoom.setRoomType(command.getRoomType());
        if (command.getPrice() != null)
            hotelRoom.setPrice(command.getPrice());
    }
}
