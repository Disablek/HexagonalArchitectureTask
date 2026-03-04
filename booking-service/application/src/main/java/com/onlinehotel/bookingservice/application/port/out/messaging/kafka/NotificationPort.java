package com.onlinehotel.bookingservice.application.port.out.messaging.kafka;

import com.onlinehotel.bookingservice.application.dto.event.BookingCreatedEvent;

public interface NotificationPort {
    void sendBookingCreated(BookingCreatedEvent event);
}
