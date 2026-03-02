package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.application.port.in.hotel.UpdateHotelUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateHotelService implements UpdateHotelUseCase {
    private final HotelRepositoryPort hotelRepositoryPort;

    public UpdateHotelService(HotelRepositoryPort hotelRepositoryPort) {
        this.hotelRepositoryPort = hotelRepositoryPort;
    }

    @Override
    public Hotel execute(Long id, UpdateHotelCommand command) throws HotelNotFoundException {
        Hotel hotel = hotelRepositoryPort.findById(id)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with id: " + id));
        updatePartially(hotel, command);
        return hotelRepositoryPort.save(hotel);
    }

    private void updatePartially(Hotel hotel, UpdateHotelCommand cmd) {
        if (cmd.name().isPresent())
            hotel.setName(cmd.name().get());
        if (cmd.address().isPresent())
            hotel.setAddress(cmd.address().get());
        if (cmd.hotelRoom().isPresent()){
            hotel.getRooms().clear();
            hotel.getRooms().addAll(cmd.hotelRoom().get());
        }
    }
}
