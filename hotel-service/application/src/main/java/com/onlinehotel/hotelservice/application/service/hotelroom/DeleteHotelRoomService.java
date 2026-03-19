package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.DeleteHotelRoomUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteHotelRoomService implements DeleteHotelRoomUseCase {
    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;
    private final TransactionalOperator transactionalOperator;

    @Override
    @CacheEvict(value = "hotelRoomCache", key = "#roomId")
    public Mono<Void> execute(Long roomId) throws HotelRoomNotFoundException {
        return transactionalOperator.execute(
                status -> hotelRoomRepositoryPort.deleteById(roomId)).then();
    }
}
