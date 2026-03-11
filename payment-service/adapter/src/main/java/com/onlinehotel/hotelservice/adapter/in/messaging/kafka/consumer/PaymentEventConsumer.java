package com.onlinehotel.hotelservice.adapter.in.messaging.kafka.consumer;


import com.onlinehotel.hotelservice.adapter.mapper.kafka.PaymentKafkaMapper;
import com.onlinehotel.hotelservice.application.dto.CreatePaymentDto;
import com.onlinehotel.hotelservice.application.dto.PaymentFailedEvent;
import com.onlinehotel.hotelservice.application.dto.PaymentProceedEvent;
import com.onlinehotel.hotelservice.application.port.in.CreatePaymentUseCase;
import com.onlinehotel.hotelservice.application.port.out.messaging.kafka.NotificationPort;
import com.onlinehotel.hotelservice.application.port.out.persistence.PaymentRepositoryPort;
import com.onlinehotel.hotelservice.model.Payment;
import com.onlinehotel.hotelservice.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentEventConsumer {
    private final CreatePaymentUseCase createPaymentUseCase;
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentKafkaMapper paymentKafkaMapper;
    private final NotificationPort notificationPort;

    @KafkaListener(topics = "booking-created")
    public void handlePayment(CreatePaymentDto dto) {
        try {
            Payment createdPayment = createPaymentUseCase.execute(paymentKafkaMapper
                    .toDomain(dto));
            if (createdPayment == null) {

            }
        }
        catch (Exception ex) {
            notificationPort.sendPaymentCreationFailed(
                    new PaymentFailedEvent(dto.getBookingId()), ex);
        }
    }

    @KafkaListener(topics = "booking-confirming-failed")
    public void refundPayment(PaymentProceedEvent event) {
        Payment payment = paymentRepositoryPort.findPaymentById(event.eventId());
        payment.setPaymentStatus(PaymentStatus.FAILED);
        paymentRepositoryPort.save(payment);
    }
}
