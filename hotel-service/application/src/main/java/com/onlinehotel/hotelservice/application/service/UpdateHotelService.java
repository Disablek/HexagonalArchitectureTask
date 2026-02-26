package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.port.in.hotel.UpdateHotelUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateHotelService implements UpdateHotelUseCase {
    private final HotelPort hotelPort;

    public UpdateHotelService(HotelPort hotelPort) {
        this.hotelPort = hotelPort;
    }

    @Override
    public Hotel execute(UpdateHotelCommand command) throws HotelNotFoundException {
        Hotel hotel = hotelPort.findById(command.hotelId()).orElseThrow(() ->
                new HotelNotFoundException("Hotel not found with id: " + command.hotelId()));

        return updatePartially(hotel, command);
    }


    public Hotel updatePartially(Hotel hotel, UpdateHotelCommand cmd) {
        return new Hotel(
                hotel.id(),
                cmd.name().orElse(hotel.name()),
                cmd.address().orElse(hotel.address()),
                cmd.hotelRoom().orElse(hotel.rooms())
        );
    }
}
