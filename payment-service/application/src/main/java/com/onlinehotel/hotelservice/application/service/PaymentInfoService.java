package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.dto.BookingDetailsDto;
import com.onlinehotel.hotelservice.application.port.in.PaymentInfoUseCase;
import com.onlinehotel.hotelservice.application.port.out.grpc.BookingServicePort;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import com.onlinehotel.hotelservice.model.Payment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentInfoService implements PaymentInfoUseCase {
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final BookingServicePort bookingServicePort;

    @Override
    public PaymentInfo execute(Long paymentId) {
        Payment payment = paymentRepositoryPort.findPaymentById(paymentId);

        BookingDetailsDto bookingDetailsDto = bookingServicePort.getBookingDetails(payment.getBookingId());

        return new PaymentInfo(
                payment.getId(),
                payment.getBookingId(),
                bookingDetailsDto.getTotalPrice(),
                payment.getPaymentDate(),
                payment.getPaymentStatus()
        );
    }
}
