package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.application.port.in.hotel.CreateHotelUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class CreateHotelService implements CreateHotelUseCase {

    private final HotelRepositoryPort hotelRepository;
    private final CacheManager cacheManager;

    @Override
    public Mono<Hotel> execute(Hotel hotel) {
        return hotelRepository.save(hotel)
                .doOnNext(this::cacheHotel);
    }

    private void cacheHotel(Hotel hotel) {
        Objects.requireNonNull(cacheManager.getCache("hotelCache"))
                .put(hotel.getId(), hotel);
    }
}

