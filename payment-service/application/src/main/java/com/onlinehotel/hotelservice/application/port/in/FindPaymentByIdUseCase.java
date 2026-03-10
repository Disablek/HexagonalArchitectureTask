package com.onlinehotel.hotelservice.application.port.in;

import com.onlinehotel.hotelservice.model.Payment;

public interface FindPaymentByIdUseCase {
    Payment execute(Long paymentId);
}
