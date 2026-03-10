package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.port.in.FindAllPaymentsUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import com.onlinehotel.hotelservice.model.Payment;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FindAllPaymentsService implements FindAllPaymentsUseCase {
    private final PaymentRepositoryPort paymentRepositoryPort;

    public FindAllPaymentsService(PaymentRepositoryPort paymentRepositoryPort) {
        this.paymentRepositoryPort = paymentRepositoryPort;
    }

    @Override
    public Set<Payment> execute() {
        return paymentRepositoryPort.findAll();
    }
}
