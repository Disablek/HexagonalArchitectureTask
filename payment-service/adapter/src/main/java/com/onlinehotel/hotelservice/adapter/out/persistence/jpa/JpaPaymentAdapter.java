package com.onlinehotel.hotelservice.adapter.out.persistence.jpa;

import com.onlinehotel.hotelservice.adapter.mapper.jpa.PaymentJpaMapper;
import com.onlinehotel.hotelservice.adapter.out.persistence.jpa.model.PaymentJpaEntity;
import com.onlinehotel.hotelservice.adapter.out.persistence.jpa.repository.PaymentJpaRepository;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import com.onlinehotel.hotelservice.model.Payment;
import com.onlinehotel.hotelservice.model.PaymentNotFoundException;
import com.onlinehotel.hotelservice.model.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class JpaPaymentAdapter implements PaymentRepositoryPort {
    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentJpaMapper paymentMapper;

    @Override
    public Payment findPaymentById(Long paymentId) throws PaymentNotFoundException  {
        return paymentMapper.toDomain(
                paymentJpaRepository.findById(paymentId)
                        .orElseThrow(
                                () -> new PaymentNotFoundException("Payment not found with id: " + paymentId)
                        )
        );
    }

    @Override
    public Payment save(Payment payment) {

        PaymentJpaEntity  paymentJpaEntity = paymentMapper.toJpaEntity(payment);
        paymentJpaEntity.setPaymentStatus(PaymentStatus.CREATED);
        paymentJpaEntity.setPaymentDate(LocalDateTime.now());
        PaymentJpaEntity savedPaymentJpaEntity = paymentJpaRepository.save(paymentJpaEntity);

        return paymentMapper.toDomain(savedPaymentJpaEntity);
    }

    @Override
    public Set<Payment> findAll() {
        return paymentMapper.toDomain(
                new HashSet<>(paymentJpaRepository.findAll())
        );
    }

    @Override
    public void proceedPayment(Long paymentId) throws PaymentNotFoundException {
        PaymentJpaEntity paymentJpaEntity = paymentJpaRepository.findById(paymentId)
                .orElseThrow(
                        () -> new PaymentNotFoundException("Payment not found with id: " + paymentId)
                );
        paymentJpaEntity.setPaymentStatus(PaymentStatus.PROCEED);
        paymentJpaRepository.save(paymentJpaEntity);
    }

    @Override
    public void cancelPayment(Long paymentId) {
        PaymentJpaEntity paymentJpaEntity = paymentJpaRepository.findById(paymentId)
                .orElseThrow(
                        () -> new PaymentNotFoundException("Payment not found with id: " + paymentId)
                );
        paymentJpaEntity.setPaymentStatus(PaymentStatus.CANCELED);
        paymentJpaRepository.save(paymentJpaEntity);
    }

    @Override
    public Payment getProceedPaymentByBookingId(Long bookingId) {

        PaymentJpaEntity paymentJpaEntity = paymentJpaRepository.getPaymentJpaEntityByBookingIdAndPaymentStatus(bookingId, PaymentStatus.PROCEED);

        return paymentMapper.toDomain(paymentJpaEntity);
    }
}
