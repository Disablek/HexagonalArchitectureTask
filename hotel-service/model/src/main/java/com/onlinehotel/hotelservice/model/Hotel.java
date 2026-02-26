package com.onlinehotel.hotelservice.model;

import java.util.HashSet;
import java.util.Set;

public record Hotel(
        Long id,
        String name,
        String address,
        Set<HotelRoom> rooms
)
{
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String address;
        private Set<HotelRoom> rooms = new HashSet<>();

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder rooms(Set<HotelRoom> rooms) { this.rooms = rooms; return this; }
        public Builder room(HotelRoom room) { this.rooms.add(room); return this; } // Singular

        public Hotel build() {
            return new Hotel(id, name, address, rooms);
        }
    }


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
