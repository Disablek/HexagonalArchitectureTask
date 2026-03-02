package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.application.port.in.hotel.DeleteHotelUseCase;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteHotelService implements DeleteHotelUseCase {
    private final HotelRepositoryPort hotelRepositoryPort;

    public DeleteHotelService(HotelRepositoryPort hotelRepositoryPort) {
        this.hotelRepositoryPort = hotelRepositoryPort;
    }

    @Override
    @CacheEvict(value = "hotelCache", key = "#id")
    public void execute(Long id) throws HotelNotFoundException {
        hotelRepositoryPort.deleteById(id);
    }
}
