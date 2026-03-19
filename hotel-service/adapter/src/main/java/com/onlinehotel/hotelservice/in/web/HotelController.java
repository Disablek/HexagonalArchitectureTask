package com.onlinehotel.hotelservice.in.web;

import com.onlinehotel.hotelservice.application.port.in.hotel.*;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.out.persistence.r2dbc.mapper.HotelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("api/hotel")
@Tag(name = "Hotels", description = "Hotel management operations")
@AllArgsConstructor
public class HotelController {

    private final CreateHotelUseCase createHotelUseCase;
    private final DeleteHotelUseCase deleteHotelUseCase;
    private final GetHotelByIdUseCase getHotelByIdUseCase;
    private final UpdateHotelUseCase updateHotelUseCase;
    private final GetAllHotelsUseCase getAllHotelsUseCase;
    private final HotelMapper hotelMapper;

    @GetMapping
    @Operation(
            description = "Get all hotels",
            summary = "Get all hotels parameters"
    )
    public Flux<Hotel> getAllHotels(){
        return getAllHotelsUseCase.execute();
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get hotel by hotel-id",
            summary = "Get hotel parameters"
    )
    public Mono<ResponseEntity<Hotel>> getHotelById(@PathVariable Long id) {
        return getHotelByIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @Operation(
            description = "Patch hotel by hotel-id",
            summary = "Patch hotel parameters"
    )
    public Mono<ResponseEntity<Hotel>> updateHotel(@NotNull @PathVariable("id") Long id,
                                             @Valid @RequestBody UpdateHotelUseCase.UpdateHotelCommand command)
            throws HotelNotFoundException {
        return updateHotelUseCase.execute(id ,command)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete hotel by hotel-id (HotelRoom - cascadeType)",
            summary = "Delete hotel"
    )
    public Mono<ResponseEntity<Void>> deleteHotel(@PathVariable Long id) {
        return deleteHotelUseCase.execute(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .onErrorResume(HotelNotFoundException.class,
                        e -> Mono.just(ResponseEntity.notFound().build()));
    }


    @PostMapping
    @Operation(summary = "Create new hotel")
    public Mono<ResponseEntity<Hotel>> createHotel(@Valid @RequestBody CreateHotelRequest request) {
        return createHotelUseCase.execute(
                hotelMapper.toDomainRequest(request)) // Error
                .map(ResponseEntity::ok);
    }


    @Data
    public static class CreateHotelRequest{
        @NotEmpty(message = "Hotelname must not be empty")
        private String hotelName;

        @NotEmpty(message = "Hoteladdress must not be empty")
        private String hotelAddress;
    }
}
