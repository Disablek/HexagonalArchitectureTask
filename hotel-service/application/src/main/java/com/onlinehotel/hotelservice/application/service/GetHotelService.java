package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.port.in.hotel.GetHotelUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelPort;
import com.onlinehotel.hotelservice.exception.InvalidArgumentException;
import com.onlinehotel.hotelservice.model.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GetHotelService implements GetHotelUseCase {
    private final HotelPort hotelPort;

    public GetHotelService(HotelPort hotelPort) {
        this.hotelPort = hotelPort;
    }

    @Override
    public Optional<Hotel> execute(Long id) throws InvalidArgumentException {
        if (id == null) {
            throw new InvalidArgumentException("id is null", "id");
        }
        return hotelPort.findById(id);
    }
}
