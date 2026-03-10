package com.onlinehotel.hotelservice.model;

import java.time.LocalDateTime;

public class Payment {
    private final Long id;

    private final Long bookingId;

    private final LocalDateTime paymentDate;

    private PaymentStatus paymentStatus;

    public Payment(Long id, Long bookingId,  LocalDateTime paymentDate, PaymentStatus paymentStatus) {
        this.id = id;
        this.bookingId = bookingId;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
}
