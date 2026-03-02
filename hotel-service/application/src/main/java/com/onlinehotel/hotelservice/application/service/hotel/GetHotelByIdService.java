package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.application.port.in.hotel.GetHotelByIdUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.exception.InvalidArgumentException;
import com.onlinehotel.hotelservice.model.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class GetHotelByIdService implements GetHotelByIdUseCase {
    private final HotelRepositoryPort hotelRepositoryPort;

    public GetHotelByIdService(HotelRepositoryPort hotelRepositoryPort) {
        this.hotelRepositoryPort = hotelRepositoryPort;
    }

    @Override
    public Optional<Hotel> execute(Long id) throws HotelNotFoundException {
        if (id == null) {
            throw new InvalidArgumentException("id is null", "id");
        }
        return hotelRepositoryPort.findById(id);
    }
}
