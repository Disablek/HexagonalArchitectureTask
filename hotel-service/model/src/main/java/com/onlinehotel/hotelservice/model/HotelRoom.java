package com.onlinehotel.hotelservice.model;

import java.math.BigDecimal;

public record HotelRoom (
    Long id,
    Integer serialNumber,
    Integer capacity,
    Long hotelId,
    BigDecimal price,
    RoomType roomType
)
{
    @Override
    public String toString(){
        return "HotelRoom{" +
                "id=" + id +
                "serialNumber=" + serialNumber.toString() +
                "capacity=" + capacity.toString() +
                "hotelId=" + hotelId.toString() +
                "price=" + price.toString() +
                "roomType=" + roomType.toString() +
                "}";

    }
}
