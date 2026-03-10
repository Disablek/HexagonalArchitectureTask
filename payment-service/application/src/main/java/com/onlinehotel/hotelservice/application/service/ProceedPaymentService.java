package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.application.dto.BookingDetailsDto;
import com.onlinehotel.hotelservice.application.port.in.ProceedPaymentUseCase;
import com.onlinehotel.hotelservice.application.port.out.grpc.BookingServicePort;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import com.onlinehotel.hotelservice.model.Payment;
import com.onlinehotel.hotelservice.model.PaymentNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ProceedPaymentService implements ProceedPaymentUseCase {
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final BookingServicePort bookingServicePort;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProceedPaymentService.class);

    @Override
    @Transactional
    public void execute(Long paymentId) throws PaymentNotFoundException {
        Payment payment = paymentRepositoryPort.findPaymentById(paymentId);
        BookingDetailsDto bookingDetailsDto = bookingServicePort.getBookingDetails(payment.getBookingId()); //ERROR

        LOGGER.info("bookingDetailsDto={}", bookingDetailsDto);

        LOGGER.info("Payment proceed with {}!", bookingDetailsDto.getTotalPrice());
        paymentRepositoryPort.proceedPayment(paymentId);
    }
}
