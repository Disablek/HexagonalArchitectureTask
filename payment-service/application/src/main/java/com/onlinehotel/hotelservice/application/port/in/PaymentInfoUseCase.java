package com.onlinehotel.hotelservice.application.port.in;

import com.onlinehotel.hotelservice.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PaymentInfoUseCase {
    PaymentInfo execute(Long paymentId);

    record PaymentInfo(
            Long id,
            Long bookingId,
            BigDecimal amount,
            LocalDateTime paymentDate,
            PaymentStatus status
    ) {}
}

