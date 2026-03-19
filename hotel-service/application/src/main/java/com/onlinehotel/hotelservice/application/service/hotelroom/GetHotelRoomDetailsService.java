package com.onlinehotel.hotelservice.application.service.hotelroom;

import com.onlinehotel.hotelservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.hotelservice.application.port.in.hotelroom.GetHotelRoomDetailsUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRoomRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetHotelRoomDetailsService implements GetHotelRoomDetailsUseCase {

    private final HotelRoomRepositoryPort hotelRoomRepositoryPort;

    @Override
    public Mono<HotelRoomDetailsDto> details(Long roomId) {
        return hotelRoomRepositoryPort.findById(roomId)
                .flatMap( hotelRoom -> {
                    return Mono.just(HotelRoomDetailsDto.builder()
                            .price(hotelRoom != null ? hotelRoom.getPrice() : null)
                            .serialNumber(hotelRoom != null ? hotelRoom.getSerialNumber() : null)
                            .roomType(hotelRoom != null ? hotelRoom.getRoomType() : null)
                            .capacity(hotelRoom != null ? hotelRoom.getCapacity() : null)
                            .build());
                });
    }
}
