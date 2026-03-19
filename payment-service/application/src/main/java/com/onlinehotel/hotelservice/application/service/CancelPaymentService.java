package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.dto.PaymentFailedEvent;
import com.onlinehotel.hotelservice.application.port.in.CancelPaymentUseCase;
import com.onlinehotel.hotelservice.application.port.out.messaging.kafka.NotificationPort;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import com.onlinehotel.hotelservice.model.Payment;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class CancelPaymentService implements CancelPaymentUseCase {
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final NotificationPort notificationPort;

    public CancelPaymentService(PaymentRepositoryPort paymentRepositoryPort, NotificationPort notificationPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.notificationPort = notificationPort;
    }

    @Override
    @CachePut(value = "paymentCache", key = "#result.id")
    public void cancelPayment(Long paymentId) {
        paymentRepositoryPort.cancelPayment(paymentId);
        Payment payment = paymentRepositoryPort.findPaymentById(paymentId);
        PaymentFailedEvent paymentFailedEvent = new PaymentFailedEvent(payment.getBookingId());
        notificationPort.sendPaymentCancelled(paymentFailedEvent);
    }
}
