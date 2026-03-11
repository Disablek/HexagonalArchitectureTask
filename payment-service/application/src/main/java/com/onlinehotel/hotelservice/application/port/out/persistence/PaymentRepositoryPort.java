package com.onlinehotel.hotelservice.application.port.out.persistence;

import com.onlinehotel.hotelservice.model.Payment;

import java.util.Set;

public interface PaymentRepositoryPort {
    Payment findPaymentById(Long paymentId);

    Payment save(Payment payment);

    Set<Payment> findAll();

    void proceedPayment(Long paymentId);

    void cancelPayment(Long paymentId);

    Payment getProceedPaymentByBookingId(Long bookingId);
}
