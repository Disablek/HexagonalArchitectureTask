package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.bookingservice.application.dto.event.BookingCreatedEvent;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.bookingservice.application.port.out.messaging.kafka.NotificationPort;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CreateBookingService implements CreateBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;
    private final NotificationPort notificationPort;
    private final HotelRoomServicePort hotelRoomServicePort;

    public CreateBookingService(BookingRepositoryPort bookingRepositoryPort, NotificationPort notificationPort, HotelRoomServicePort hotelRoomServicePort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.notificationPort = notificationPort;
        this.hotelRoomServicePort = hotelRoomServicePort;
    }

    @Override
    public Booking execute(Booking booking) {

        HotelRoomDetailsDto hotelRoomDetailsDto = hotelRoomServicePort.getHotelDetails(
                booking.getHotelId(), booking.getHotelRoomId()
        );

        BigDecimal totalPrice = hotelRoomDetailsDto.price()
                .multiply(BigDecimal.valueOf(booking.getDateRange().nights()));

        booking.setTotalPrice(totalPrice);

        Booking savedBooking = bookingRepositoryPort.save(booking);

        BookingCreatedEvent event = BookingCreatedEvent.from(savedBooking);
        notificationPort.sendBookingCreated(event);
        return savedBooking;
    }
}
