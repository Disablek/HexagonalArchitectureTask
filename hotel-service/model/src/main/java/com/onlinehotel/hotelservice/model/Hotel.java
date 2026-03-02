package com.onlinehotel.hotelservice.model;

import java.util.Set;

public class Hotel {

    public Hotel(Long id, String name, String address, Set<HotelRoom> rooms) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rooms = rooms;
    }

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Hotel() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    private String name;

    private String address;

    private Set<HotelRoom> rooms;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<HotelRoom> getRooms() {
        return rooms;
    }

    public void setRooms(Set<HotelRoom> rooms) {
        this.rooms = rooms;
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
