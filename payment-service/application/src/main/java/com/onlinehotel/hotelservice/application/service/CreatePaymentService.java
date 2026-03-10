package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.port.in.CreatePaymentUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import com.onlinehotel.hotelservice.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreatePaymentService implements CreatePaymentUseCase {
    private final PaymentRepositoryPort paymentRepositoryPort;

    @Override
    @Transactional
    public Payment execute(Payment request) {
        return paymentRepositoryPort.save(request);
    }
}
