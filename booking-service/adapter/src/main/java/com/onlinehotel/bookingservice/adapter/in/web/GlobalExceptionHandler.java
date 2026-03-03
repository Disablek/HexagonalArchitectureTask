package com.onlinehotel.bookingservice.adapter.in.web;

import com.onlinehotel.bookingservice.exception.BookingNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {
//
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleUniqueConstraint(DataIntegrityViolationException ex) {
//        if (ex.getMessage().contains("unique_serial_per_hotel")) {
//            throw new DuplicateSerialNumberException("SerialNumber уже существует в этом отеле");
//        }
//        throw ex;
//    }

    @ExceptionHandler(BookingNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleHotelAlreadyExists(BookingNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("BOOKING_NOT_FOUND", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

}

@Data
@AllArgsConstructor
class ErrorResponse {
    private String code;
    private String message;
}
