package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.dto.event.BookingCreatedEvent;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.bookingservice.application.port.out.messaging.kafka.NotificationPort;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class CreateBookingService implements CreateBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;

    public CreateBookingService(BookingRepositoryPort bookingRepositoryPort, NotificationPort notificationPort,
                                HotelRoomServicePort hotelRoomServicePort, CacheManager cacheManager) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.notificationPort = notificationPort;
        this.hotelRoomServicePort = hotelRoomServicePort;
        this.cacheManager = cacheManager;
    }

    private final NotificationPort notificationPort;
    private final HotelRoomServicePort hotelRoomServicePort;
    private final CacheManager cacheManager;

    @Override
    public Mono<Booking> execute(Booking booking) {
        return hotelRoomServicePort.getHotelDetails(booking.getHotelId(), booking.getHotelRoomId())
                .map(details -> {
                    Booking newBooking = new Booking(booking.getId(), booking.getHotelId(), booking.getHotelRoomId(),
                            booking.getCheckIn(), booking.getCheckOut(), booking.getBookingStatus(), booking.getTotalPrice());
                    BigDecimal totalPrice = details.price()
                            .multiply(BigDecimal.valueOf(booking.nights()));
                    newBooking.setTotalPrice(totalPrice);
                    return newBooking;
                })
                .flatMap(bookingRepositoryPort::save)
                .publishOn(Schedulers.boundedElastic())  // Side-effects асинхронно
                .doOnNext(savedBooking -> {
                    cacheBooking(savedBooking);
                    notificationPort.sendBookingCreated(
                            BookingCreatedEvent.from(savedBooking));
                });
    }

    private void cacheBooking(Booking booking) {
        Objects.requireNonNull(cacheManager.getCache("bookingCache"))
                .put(booking.getId(), booking);
    }
}

