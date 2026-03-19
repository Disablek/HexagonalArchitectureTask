package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.application.port.in.hotel.GetHotelByIdUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class GetHotelByIdService implements GetHotelByIdUseCase {
    private final HotelRepositoryPort hotelRepositoryPort;

    public GetHotelByIdService(HotelRepositoryPort hotelRepositoryPort) {
        this.hotelRepositoryPort = hotelRepositoryPort;
    }

    @Override
    @Cacheable(value = "hotelCache", key = "#hotelId")
    public Mono<Hotel> execute(Long hotelId) throws HotelNotFoundException {
        return hotelRepositoryPort.findById(hotelId)
                .switchIfEmpty(Mono.error(new HotelNotFoundException("Hotel not found with id: " + hotelId)));
    }
}
