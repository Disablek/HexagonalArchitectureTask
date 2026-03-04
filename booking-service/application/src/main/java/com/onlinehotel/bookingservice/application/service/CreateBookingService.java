package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.dto.event.BookingCreatedEvent;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.messaging.kafka.NotificationPort;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateBookingService implements CreateBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;
    private final NotificationPort notificationPort;

    public CreateBookingService(BookingRepositoryPort bookingRepositoryPort, NotificationPort notificationPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.notificationPort = notificationPort;
    }

    @Override
    public Booking execute(Booking booking) {
        Booking savedBooking = bookingRepositoryPort.save(booking);

        BookingCreatedEvent event = BookingCreatedEvent.from(savedBooking);
        notificationPort.sendBookingCreated(event);

        return savedBooking;
    }
}
