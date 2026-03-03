package com.onlinehotel.bookingservice.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateRange {
    private LocalDate checkIn;

    private LocalDate checkOut;

    public long nights(){
        return ChronoUnit.DAYS.between(checkIn,checkOut);
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

    public DateRange() {
    }

    public DateRange(LocalDate checkIn, LocalDate checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
