package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.port.in.FindPaymentByIdUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import com.onlinehotel.hotelservice.model.Payment;
import com.onlinehotel.hotelservice.model.PaymentNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindPaymentByIdService implements FindPaymentByIdUseCase {
    private final PaymentRepositoryPort paymentRepositoryPort;

    public FindPaymentByIdService(PaymentRepositoryPort paymentRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
    }

    @Override
    public Payment execute(Long paymentId) throws PaymentNotFoundException {
        return paymentRepositoryPort.findPaymentById(paymentId);
    }
}
