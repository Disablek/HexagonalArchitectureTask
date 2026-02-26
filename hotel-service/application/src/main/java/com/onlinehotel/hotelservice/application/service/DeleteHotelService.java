package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.port.out.persistence.HotelPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.application.port.in.hotel.DeleteHotelUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteHotelService implements DeleteHotelUseCase {
    private final HotelPort hotelPort;

    public DeleteHotelService(HotelPort hotelPort) {
        this.hotelPort = hotelPort;
    }

    @Override
    public void execute(Long id) throws HotelNotFoundException {
        if (hotelPort.findById(id).isEmpty()) {
            throw new HotelNotFoundException("Hotel not found");
        }

        hotelPort.deleteById(id);
    }
}
