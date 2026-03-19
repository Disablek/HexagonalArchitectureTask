package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.FailBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.exception.BookingNotFoundException;
import com.onlinehotel.bookingservice.model.BookingStatus;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
public class FailBookingService implements FailBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;
    private final TransactionalOperator transactionalOperator;

    public FailBookingService(BookingRepositoryPort bookingRepositoryPort, TransactionalOperator transactionalOperator) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    @CachePut(value = "bookingCache", key = "#bookingId")
    public Mono<Void> execute(Long bookingId) {
        return transactionalOperator.transactional(
                bookingRepositoryPort.findById(bookingId)
                        .switchIfEmpty(Mono.error(
                                new BookingNotFoundException("Booking not found with id: " + bookingId)))
                        .flatMap(booking -> {
                            booking.setBookingStatus(BookingStatus.FAILED);
                            return bookingRepositoryPort.save(booking);
                        }))
                .map(Mono::just)
                .then();
    }
}
