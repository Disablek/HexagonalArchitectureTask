package com.onlinehotel.hotelservice.application.port.in;

import com.onlinehotel.hotelservice.model.Payment;

import java.util.Set;

public interface FindAllPaymentsUseCase {
    Set<Payment> execute();
}
