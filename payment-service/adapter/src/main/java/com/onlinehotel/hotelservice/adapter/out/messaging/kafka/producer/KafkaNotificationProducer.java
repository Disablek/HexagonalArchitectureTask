package com.onlinehotel.hotelservice.adapter.out.messaging.kafka.producer;


import com.onlinehotel.hotelservice.application.dto.PaymentFailedEvent;
import com.onlinehotel.hotelservice.application.dto.PaymentProceedEvent;
import com.onlinehotel.hotelservice.application.port.out.messaging.kafka.NotificationPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaNotificationProducer implements NotificationPort {
    private final KafkaTemplate<Long, PaymentProceedEvent> proceedTemplate;

    private final KafkaTemplate<Long, Long> failedTemplate;

    @Override
    public void sendPaymentProceed(PaymentProceedEvent event) {
        proceedTemplate.send("payment_proceed", event.eventId(), event);
    }

    @Override
    public void sendPaymentCreationFailed(PaymentFailedEvent event, Throwable ex) {
        log.warn("Couldn't create payment. Proceeding rollback", ex);
        failedTemplate.send("payment-creating-failed", event.bookingId());
    }
}

