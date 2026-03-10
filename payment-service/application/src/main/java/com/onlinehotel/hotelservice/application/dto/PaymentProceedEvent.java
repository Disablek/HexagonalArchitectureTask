package com.onlinehotel.hotelservice.application.dto;

import com.onlinehotel.hotelservice.model.Payment;
import com.onlinehotel.hotelservice.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Random;

public record PaymentProceedEvent (
        Long eventId,
        Long paymentId,
        Long bookingId,
        PaymentStatus paymentStatus,
        LocalDateTime paymentDate
)
{
    public static PaymentProceedEvent from(Payment payment){
        return new PaymentProceedEvent(
                new Random().nextLong(),
                payment.getId(),
                payment.getBookingId(),
                payment.getPaymentStatus(),
                payment.getPaymentDate()
        );
    }
}
