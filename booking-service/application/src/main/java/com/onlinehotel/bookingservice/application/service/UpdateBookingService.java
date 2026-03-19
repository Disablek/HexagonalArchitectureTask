package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.UpdateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.exception.BookingNotFoundException;
import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.BookingStatus;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class UpdateBookingService implements UpdateBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;
    private final HotelRoomServicePort hotelRoomServicePort;
    private final TransactionalOperator transactionalOperator;
    private final CacheManager cacheManager;

    public UpdateBookingService(BookingRepositoryPort bookingRepositoryPort, HotelRoomServicePort hotelRoomServicePort,
                                TransactionalOperator transactionalOperator, CacheManager cacheManager) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.hotelRoomServicePort = hotelRoomServicePort;
        this.transactionalOperator = transactionalOperator;
        this.cacheManager = cacheManager;
    }
    @Override
    public Mono<Booking> execute(Long id, UpdateBookingCommand command) {
        return transactionalOperator.transactional(
                bookingRepositoryPort.findById(id)
                        .switchIfEmpty(Mono.error(() ->
                                new BookingNotFoundException("Booking not found: " + id)))
                        .flatMap(booking -> {
                            if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
                                return Mono.error(new IllegalStateException(
                                        "Booking " + id + " is already confirmed"));
                            }

                            updatePartially(booking, command);

                            return hotelRoomServicePort.getHotelDetails(
                                            booking.getHotelId(), booking.getHotelRoomId())
                                    .map(details -> {
                                        BigDecimal totalPrice = details.price()
                                                .multiply(BigDecimal.valueOf(booking.nights()));
                                        booking.setTotalPrice(totalPrice);
                                        return booking;
                                    })
                                    .flatMap(bookingRepositoryPort::save);
                        })
                        .doOnNext(this::cacheBooking)
        );
    }

    private void cacheBooking(Booking booking) {
        Objects.requireNonNull(cacheManager.getCache("bookingCache"))
                .put(booking.getId(), booking);
    }

    void updatePartially(Booking booking, UpdateBookingCommand command) {
        if (command.hotelRoomId().isPresent())
            booking.setHotelRoomId(command.hotelRoomId().get());
        if (command.checkIn().isPresent())
            booking.setCheckIn(command.checkIn().get());
        if (command.checkOut().isPresent())
            booking.setCheckOut(command.checkOut().get());
    }
}
