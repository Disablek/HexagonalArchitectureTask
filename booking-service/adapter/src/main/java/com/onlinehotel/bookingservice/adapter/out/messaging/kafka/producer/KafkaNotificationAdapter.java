package com.onlinehotel.bookingservice.adapter.out.messaging.kafka.producer;

import com.onlinehotel.bookingservice.application.dto.event.BookingCreatedEvent;
import com.onlinehotel.bookingservice.application.port.out.messaging.kafka.NotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaNotificationAdapter implements NotificationPort {
    private final KafkaTemplate<Long, BookingCreatedEvent> kafkaTemplate;

    @Override
    public void sendBookingCreated(BookingCreatedEvent event) {
        kafkaTemplate.send("booking-created", event.bookingId(), event);
    }
}
