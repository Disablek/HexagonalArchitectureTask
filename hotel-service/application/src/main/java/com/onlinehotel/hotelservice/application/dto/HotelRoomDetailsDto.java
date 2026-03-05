package com.onlinehotel.hotelservice.application.dto;

import com.onlinehotel.hotelservice.model.RoomType;
import lombok.*;

import java.math.BigDecimal;

@Builder
public record HotelRoomDetailsDto(
        Integer serialNumber,
        Integer capacity,
        RoomType roomType,
        BigDecimal price)
{
}
