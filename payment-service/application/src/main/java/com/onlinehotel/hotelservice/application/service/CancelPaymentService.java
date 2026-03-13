package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.port.in.CancelPaymentUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
public class CancelPaymentService implements CancelPaymentUseCase {
    private final PaymentRepositoryPort paymentRepositoryPort;

    public CancelPaymentService(PaymentRepositoryPort paymentRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
    }

    @Override
    @CachePut(value = "paymentCache", key = "#result.id")
    public void cancelPayment(Long paymentId) {
        paymentRepositoryPort.cancelPayment(paymentId);
    }
}
