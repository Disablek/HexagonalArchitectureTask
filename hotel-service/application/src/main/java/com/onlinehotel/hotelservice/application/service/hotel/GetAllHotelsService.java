package com.onlinehotel.hotelservice.application.service.hotel;

import com.onlinehotel.hotelservice.application.port.in.hotel.GetAllHotelsUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.model.Hotel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@Service
public class GetAllHotelsService implements GetAllHotelsUseCase {
    private final HotelRepositoryPort hotelRepositoryPort;

    public GetAllHotelsService(HotelRepositoryPort hotelRepositoryPort) {
        this.hotelRepositoryPort = hotelRepositoryPort;
    }

    @Override
    public Flux<Hotel> execute() {
        return hotelRepositoryPort.findAll();
    }
}
