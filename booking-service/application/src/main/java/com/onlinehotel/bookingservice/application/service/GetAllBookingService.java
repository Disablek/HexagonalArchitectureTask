package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.port.in.GetAllBookingsUseCase;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.Set;

@Service
public class GetAllBookingService implements GetAllBookingsUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;

    public GetAllBookingService(BookingRepositoryPort bookingRepositoryPort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
    }


    @Override
    public Flux<Booking> execute()
    {
        return bookingRepositoryPort.findAll();
    }
}
