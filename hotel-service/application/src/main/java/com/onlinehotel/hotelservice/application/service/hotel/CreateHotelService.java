package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.application.port.in.hotel.CreateHotelUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateHotelService implements CreateHotelUseCase {

    private final HotelRepositoryPort hotelRepository;

    public CreateHotelService(HotelRepositoryPort hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    @CachePut(value = "hotelCache", key = "#result.id")
    public Hotel execute(Hotel hotel) {
        return hotelRepository.save(hotel);
    }
}
