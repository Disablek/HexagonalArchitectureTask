package com.onlinehotel.hotelservice.model;

import java.util.Set;

public class Hotel {

    public Hotel(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
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


}
