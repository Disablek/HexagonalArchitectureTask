package com.onlinehotel.bookingservice.adapter.in.rest;

import com.onlinehotel.bookingservice.adapter.mapper.r2dbc.BookingR2DBCMapper;
import com.onlinehotel.bookingservice.application.port.in.*;
import com.onlinehotel.bookingservice.exception.BookingNotFoundException;
import com.onlinehotel.bookingservice.model.Booking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
@Slf4j
@Tag(name = "Bookings", description = "Booking management operations")
public class BookingController {
    private final GetBookingByIdUseCase getBookingByIdUseCase;
    private final GetAllBookingsUseCase getAllBookingsUseCase;
    private final CreateBookingUseCase createBookingUseCase;
    private final UpdateBookingUseCase updateBookingUseCase;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final BookingR2DBCMapper bookingR2DBCMapper;

    @GetMapping
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public Flux<Booking> getAllBookings(){
        return getAllBookingsUseCase.execute();
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get booking by booking-id",
            summary = "Get all booking parameters"
    )
    public Mono<ResponseEntity<Booking>> getBookingById(@PathVariable("id") Long id) throws BookingNotFoundException {
        return getBookingByIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Create booking"
    )
    public Mono<ResponseEntity<Booking>> createBooking(@RequestBody CreateBookingUseCase.CreateBookingCommand command) {
        return createBookingUseCase.execute(bookingR2DBCMapper.toDomain(command))
                .map(ResponseEntity.status(HttpStatus.CREATED)::body);

    }

    @PatchMapping("/{id}")
    @Operation(
            description = "Patch booking by booking-id",
            summary = "Patch booking parameters"
    )
    public Mono<ResponseEntity<Booking>> updateBooking(
            @PathVariable("id") Long id,
            @RequestBody UpdateBookingUseCase.UpdateBookingCommand command) throws BookingNotFoundException {
        return updateBookingUseCase.execute(id,command)
                .map(ResponseEntity::ok)
                .onErrorResume(BookingNotFoundException.class,
                        e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Cancelling booking",
            summary = "Change booking status to CANCELED"
    )
    public Mono<ResponseEntity<Void>> cancelBooking(@PathVariable("id") Long id) throws BookingNotFoundException {
        return cancelBookingUseCase.execute(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .onErrorResume(BookingNotFoundException.class,
                        e -> Mono.just(ResponseEntity.notFound().build()));
    }
}
