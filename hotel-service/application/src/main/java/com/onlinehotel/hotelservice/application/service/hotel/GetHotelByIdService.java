package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.application.port.in.hotel.GetHotelByIdUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.exception.InvalidArgumentException;
import com.onlinehotel.hotelservice.model.Hotel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "hotelCache", key = "#hotelId")
    @Transactional(readOnly = true)
    public Hotel execute(Long hotelId) throws HotelNotFoundException {
        if (hotelId == null) {
            throw new InvalidArgumentException("id is null", "id");
        }
        System.out.println("hotelId: " + hotelId);
        return hotelRepositoryPort.findById(hotelId);
    }
}
