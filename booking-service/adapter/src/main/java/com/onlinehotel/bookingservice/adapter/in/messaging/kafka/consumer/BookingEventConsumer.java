package com.onlinehotel.bookingservice.adapter.in.messaging.kafka.consumer;

import com.onlinehotel.bookingservice.adapter.mapper.messaging.kafka.BookingKafkaMapper;
import com.onlinehotel.bookingservice.application.dto.CancelBookingDto;
import com.onlinehotel.bookingservice.application.dto.ConfirmBookingDto;
import com.onlinehotel.bookingservice.application.port.in.CancelBookingUseCase;
import com.onlinehotel.bookingservice.application.port.in.ConfirmBookingUseCase;
import com.onlinehotel.bookingservice.application.port.in.FailBookingUseCase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class BookingEventConsumer {
    private final ConfirmBookingUseCase confirmBookingUseCase;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final FailBookingUseCase failBookingUseCase;

    @KafkaListener(topics = "payment_proceed")
    public void handlePayment(ConfirmBookingDto dto) {
        confirmBookingUseCase.confirmBooking(
                dto.bookingId());
    }

    @KafkaListener(topics = "payment-creating-failed", containerFactory = "stringFactory")
    public void handlePaymentCreationFailed(Long bookingId) {
        log.warn("Couldn't create payment. Proceeding rollback");
        failBookingUseCase.execute(bookingId);
    }

    @KafkaListener(topics = "payment-cancel", containerFactory = "stringFactory")
    public void handlePaymentCancelled(Long bookingId) {
        log.info("payment-cancel");
        cancelBookingUseCase.execute(bookingId);
    }
}
