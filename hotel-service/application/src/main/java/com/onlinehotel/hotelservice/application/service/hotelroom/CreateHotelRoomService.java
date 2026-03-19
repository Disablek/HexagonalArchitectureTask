package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.CreateHotelRoomUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.DuplicateSerialNumberException;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.Objects;


@Service
@AllArgsConstructor
public class CreateHotelRoomService implements CreateHotelRoomUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;
    private final HotelRepositoryPort hotelRepositoryPort;
    private final CacheManager cacheManager;
    private final TransactionalOperator transactionalOperator;


    @Override
    public Mono<HotelRoom> execute(Long hotelId, HotelRoom hotelRoom) {
        hotelRoom.setHotelId(hotelId);

        return transactionalOperator.transactional(
                hotelRepositoryPort.findById(hotelId)
                        .switchIfEmpty(Mono.error(new HotelNotFoundException("Hotel not found: " + hotelId)))
                        .flatMap(hotel ->
                                hotelRoomRepositoryPort.existsByHotelIdAndSerialNumber(hotelId, hotelRoom.getSerialNumber())
                                        .flatMap(exists -> {
                                            if (exists) {
                                                return Mono.error(new DuplicateSerialNumberException(
                                                        "SerialNumber " + hotelRoom.getSerialNumber() + " already exists"));
                                            }
                                            return hotelRoomRepositoryPort.save(hotelRoom);
                                        })
                        )
        ).doOnNext(this::cacheHotelRoom);
    }

    private void cacheHotelRoom(HotelRoom hotel) {
        Objects.requireNonNull(cacheManager.getCache("hotelCache"))
                .put(hotel.getId(), hotel);
    }
}
