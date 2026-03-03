package com.onlinehotel.bookingservice.adapter.in.web;

import com.onlinehotel.bookingservice.adapter.out.persistence.jpa.mapper.BookingMapper;
import com.onlinehotel.bookingservice.application.port.in.*;
import com.onlinehotel.bookingservice.exception.BookingNotFoundException;
import com.onlinehotel.bookingservice.model.Booking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
@Tag(name = "Bookings", description = "Booking management operations")
public class BookingController {
    private final GetBookingByIdUseCase getBookingByIdUseCase;
    private final GetAllBookingsUseCase getAllBookingsUseCase;
    private final CreateBookingUseCase createBookingUseCase;
    private final UpdateBookingUseCase updateBookingUseCase;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final BookingMapper bookingMapper;

    @GetMapping
    @Operation(
            description = "Get all bookings",
            summary = "Get all bookings parameters"
    )
    public ResponseEntity<Set<Booking>> getAllBookings(){
        return ResponseEntity.ok(getAllBookingsUseCase.execute());
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get booking by booking-id",
            summary = "Get all booking parameters"
    )
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") Long id) throws BookingNotFoundException {
        return ResponseEntity.ok().body(getBookingByIdUseCase.execute(id));
    }

    @PostMapping
    @Operation(
            summary = "Create booking"
    )
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingUseCase.CreateBookingCommand command) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createBookingUseCase.execute(
                        bookingMapper.toDomain(command)
                ));
    }

    @PatchMapping("/{id}")
    @Operation(
            description = "Patch booking by booking-id",
            summary = "Patch booking parameters"
    )
    public ResponseEntity<Booking> updateBooking(
            @PathVariable("id") Long id,
            @RequestBody UpdateBookingUseCase.UpdateBookingCommand command) throws BookingNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updateBookingUseCase.execute(id, command));
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Cancelling booking",
            summary = "Change booking status to CANCELED"
    )
    public ResponseEntity<?> cancelBooking(@PathVariable("id") Long id) throws BookingNotFoundException {
        cancelBookingUseCase.execute(id);
        return ResponseEntity.ok().build();
    }
}
