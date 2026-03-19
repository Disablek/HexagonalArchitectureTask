package com.onlinehotel.bookingservice.model;

import com.onlinehotel.bookingservice.exception.BookingException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {
    private Long id;

    private Long hotelId;

    private Long hotelRoomId;

    private LocalDate checkIn;

    private LocalDate checkOut;

    public long nights(){
        return ChronoUnit.DAYS.between(checkIn,checkOut) + 1;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    private BookingStatus bookingStatus = BookingStatus.PENDING;

    private BigDecimal totalPrice;

    public Booking() {
    }

    public Booking(Long id, Long hotelId, Long hotelRoomId,LocalDate checkIn, LocalDate checkOut,   BookingStatus bookingStatus, BigDecimal totalPrice) {
        this.id = id;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.hotelRoomId = hotelRoomId;
        this.bookingStatus = bookingStatus;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getHotelRoomId() {
        return hotelRoomId;
    }

    public void setHotelRoomId(Long hotelRoomId) {
        this.hotelRoomId = hotelRoomId;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void confirmBooking(){
        if (!bookingStatus.equals(BookingStatus.PENDING))
            throw new BookingException("Cannot confirm booking");
        this.bookingStatus = BookingStatus.CONFIRMED;
    }

    public void cancelBooking(){
        this.bookingStatus = BookingStatus.CANCELLED;
    }
}
