package com.onlinehotel.hotelservice.adapter.out.persistence.jpa.repository;

import com.onlinehotel.hotelservice.adapter.out.persistence.jpa.model.PaymentJpaEntity;
import com.onlinehotel.hotelservice.model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {
    PaymentJpaEntity getPaymentJpaEntityByBookingIdAndPaymentStatus(Long bookingId, PaymentStatus paymentStatus);
}
