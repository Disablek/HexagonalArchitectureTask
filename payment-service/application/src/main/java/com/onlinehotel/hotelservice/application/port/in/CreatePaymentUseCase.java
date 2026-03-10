package com.onlinehotel.hotelservice.application.port.in;

import com.onlinehotel.hotelservice.model.Payment;
import lombok.Builder;

import java.math.BigDecimal;

public interface CreatePaymentUseCase
{
    Payment execute(Payment request);

    @Builder
    record CreatePaymentCommand(
            Long bookingId,
            BigDecimal amount
    ){}
}
