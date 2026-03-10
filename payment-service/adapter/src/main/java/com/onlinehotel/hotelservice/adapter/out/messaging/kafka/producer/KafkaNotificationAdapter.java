package com.onlinehotel.hotelservice.adapter.out.messaging.kafka.producer;


import com.onlinehotel.hotelservice.application.dto.PaymentProceedEvent;
import com.onlinehotel.hotelservice.application.port.out.messaging.kafka.NotificationPort;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaNotificationAdapter implements NotificationPort {
    private final KafkaTemplate<Long, PaymentProceedEvent> kafkaTemplate;

    @Override
    public void sendPaymentProceed(PaymentProceedEvent event) {
        kafkaTemplate.send("payment_proceed", event.eventId(), event);
    }
}
