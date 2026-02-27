package com.onlinehotel.hotelservice.in.web;

import com.onlinehotel.hotelservice.exception.HotelAlreadyExists;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.exception.HotelRoomAlreadyExists;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HotelAlreadyExists.class)
    public Mono<ResponseEntity<ErrorResponse>> handleHotelAlreadyExists(HotelAlreadyExists ex) {
        ErrorResponse error = new ErrorResponse("HOTEL_ALREADY_EXISTS", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
    }

    @ExceptionHandler(HotelNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleHotelNotFound(HotelNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("HOTEL_NOT_FOUND", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }


    @ExceptionHandler(HotelRoomNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleHotelRoomNotFound(HotelRoomNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("HOTELROOM_NOT_FOUND", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(error));
    }

    @ExceptionHandler(HotelRoomAlreadyExists.class)
    public Mono<ResponseEntity<ErrorResponse>> handleRoomAlreadyExists(HotelRoomAlreadyExists ex) {
        ErrorResponse error = new ErrorResponse("HOTELROOM_ALREADY_EXISTS", ex.getMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error));
    }


}

@Data
@AllArgsConstructor
class ErrorResponse {
    private String code;
    private String message;
}
