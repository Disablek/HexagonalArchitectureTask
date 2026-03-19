package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.application.port.in.hotel.UpdateHotelUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateHotelService implements UpdateHotelUseCase {
    private final HotelRepositoryPort hotelRepositoryPort;

    public UpdateHotelService(HotelRepositoryPort hotelRepositoryPort) {
        this.hotelRepositoryPort = hotelRepositoryPort;
    }

    @Override
    @CachePut(value = "hotelCache", key = "#id")
    public Mono<Hotel> execute(Long id, UpdateHotelCommand command) {
        return hotelRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new HotelNotFoundException("Hotel not found with id: " + id)))
                .map(hotel -> applyPartialUpdate(hotel, command))
                .flatMap(hotelRepositoryPort::save);
    }

    private Hotel applyPartialUpdate(Hotel hotel, UpdateHotelCommand cmd) {
        cmd.name().ifPresent(hotel::setName);
        cmd.address().ifPresent(hotel::setAddress);

        return hotel;
    }
}

