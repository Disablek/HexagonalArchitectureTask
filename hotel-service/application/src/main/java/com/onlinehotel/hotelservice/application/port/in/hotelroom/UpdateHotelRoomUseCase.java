package com.onlinehotel.hotelservice.application.port.in.hotelroom;

import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.model.RoomType;
import lombok.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UpdateHotelRoomUseCase {
    Mono<HotelRoom> execute(Long id, UpdateHotelRoomCommand command) throws HotelRoomNotFoundException;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class UpdateHotelRoomCommand {
        private Long id;

        private Integer serialNumber;

        private Integer capacity;

        private BigDecimal price;

        private RoomType roomType;
    }
}
