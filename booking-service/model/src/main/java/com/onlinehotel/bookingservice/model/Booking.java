package com.onlinehotel.bookingservice.model;

import com.onlinehotel.bookingservice.exception.BookingException;

import java.math.BigDecimal;

public class Booking {
    private Long id;

    private Long hotelId;

    private Long hotelRoomId;

    private DateRange dateRange;

    private BookingStatus bookingStatus = BookingStatus.PENDING;

    private BigDecimal totalPrice;

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

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
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
