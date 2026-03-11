package com.onlinehotel.bookingservice.adapter.in.messaging.kafka.consumer;

import com.onlinehotel.bookingservice.adapter.mapper.messaging.kafka.BookingKafkaMapper;
import com.onlinehotel.bookingservice.application.dto.ConfirmBookingDto;
import com.onlinehotel.bookingservice.application.port.in.CancelBookingUseCase;
import com.onlinehotel.bookingservice.application.port.in.ConfirmBookingUseCase;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookingEventConsumer {
    private final ConfirmBookingUseCase confirmBookingUseCase;
    private final CancelBookingUseCase cancelBookingUseCase;

    @KafkaListener(topics = "payment_proceed")
    public void handlePayment(ConfirmBookingDto dto) {
        confirmBookingUseCase.confirmBooking(
                dto.bookingId());
    }

    @KafkaListener(topics = "payment-creating-failed")
    public void handlePaymentCreationFailed(Long bookingId) {
        cancelBookingUseCase.execute(bookingId);
    }
}
