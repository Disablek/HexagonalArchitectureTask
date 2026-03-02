package com.onlinehotel.hotelservice.model;

import java.math.BigDecimal;
import java.util.Objects;

public class HotelRoom {
    private Long id;
    private Integer serialNumber;
    private Integer capacity;
    private Long hotelId;
    private BigDecimal price;
    private RoomType roomType;

    public HotelRoom(
            Long id,
            Integer serialNumber,
            Integer capacity,
            Long hotelId,
            BigDecimal price,
            RoomType roomType
    ) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.capacity = capacity;
        this.hotelId = hotelId;
        this.price = price;
        this.roomType = roomType;
    }
    public HotelRoom() {

    }


    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "HotelRoom{" +
                "id=" + id +
                "serialNumber=" + serialNumber.toString() +
                "capacity=" + capacity.toString() +
                "hotelId=" + hotelId.toString() +
                "price=" + price.toString() +
                "roomType=" + roomType.toString() +
                "}";

    }

    public Long getId() {
        return id;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (HotelRoom) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.serialNumber, that.serialNumber) &&
                Objects.equals(this.capacity, that.capacity) &&
                Objects.equals(this.hotelId, that.hotelId) &&
                Objects.equals(this.price, that.price) &&
                Objects.equals(this.roomType, that.roomType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, capacity, hotelId, price, roomType);
    }

}
