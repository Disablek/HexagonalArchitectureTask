package com.onlinehotel.hotelservice.model;

import java.util.Set;

public record Hotel(
        Long id,
        String name,
        String address,
        Set<HotelRoom> rooms
)
{

    @Override
    public String toString(){
        return "Hotel{"+
                "id=" + id +
                "name='" + name +
                "address=" + address +
                "rooms=" + rooms.toString() +
                "}";
    }

}
