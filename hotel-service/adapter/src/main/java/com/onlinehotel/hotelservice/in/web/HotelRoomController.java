package com.onlinehotel.hotelservice.in.web;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.*;
import com.onlinehotel.hotelservice.exception.DuplicateSerialNumberException;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.exception.InvalidArgumentException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.model.RoomType;
import com.onlinehotel.hotelservice.out.persistence.r2dbc.mapper.HotelRoomMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Tag(name = "HotelRooms", description = "HotelRooms management operations")
@RestController
@RequestMapping("/api/hotelRoom")
@AllArgsConstructor
public class HotelRoomController {

    private final GetAllHotelRoomsUseCase getAllHotelRoomsUseCase;
    private final CreateHotelRoomUseCase createHotelRoomUseCase;
    private final UpdateHotelRoomUseCase updateHotelRoomUseCase;
    private final DeleteHotelRoomUseCase deleteHotelRoomUseCase;
    private final GetAllHotelRoomsByHotelIdUseCase getAllHotelRoomsByHotelIdUseCase;
    private final GetHotelRoomByIdUseCase getHotelRoomByIdUseCase;
    private final HotelRoomMapper hotelRoomMapper;

    @GetMapping("/{hotelId}/rooms")
    @Operation(
            description = "Get all hotelRooms by hotel-id",
            summary = "Get all hotelRooms parameters in hotel"
    )
    public Flux<HotelRoom> getAllHotelRoomsByHotelId(@PathVariable("hotelId") Long hotelId)
            throws HotelRoomNotFoundException {
        return getAllHotelRoomsByHotelIdUseCase.execute(hotelId);
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get hotelRoom by hotelRoom-id",
            summary = "Get hotelRoom parameters"
    )
    public Mono<ResponseEntity<HotelRoom>> getHotelRoomById(@NotNull @PathVariable("id") Long hotelRoomId)
            throws HotelRoomNotFoundException {
        return getHotelRoomByIdUseCase.execute(hotelRoomId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(
            description = "Get all hotelRooms",
            summary = "Get all hotelRooms"
    )
    public Flux<HotelRoom> getAllHotelRooms(){
        return getAllHotelRoomsUseCase.execute();
    }

    @PostMapping("/{hotelId}/rooms")
    @Operation(summary = "Create hotelRoom in hotel")
    public Mono<ResponseEntity<HotelRoom>> createHotelRoom(
            @Valid @RequestBody CreateHotelRoomRequest createHotelRoomRequest,
            @PathVariable("hotelId") Long hotelId)
                                            throws DuplicateSerialNumberException, InvalidArgumentException {
        return createHotelRoomUseCase.execute(
                        hotelId,
                        hotelRoomMapper.toDomain(createHotelRoomRequest))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @Operation(
            description = "Patch hotelRoom by hotelRoom-id",
            summary = "Patch hotelRoom parameters"
    )
    public Mono<ResponseEntity<HotelRoom>> updateHotelRoomById(@NotNull @PathVariable("id") Long hotelRoomId,
                                                         @Valid @RequestBody UpdateHotelRoomUseCase.UpdateHotelRoomCommand command)
            throws HotelRoomNotFoundException {
        return updateHotelRoomUseCase.execute(hotelRoomId, command)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete hotelRoom by hotelRoom-id",
            summary = "Delete hotelRoom"
    )
    public Mono<ResponseEntity<Void>> deleteHotelRoomById(@NotNull @PathVariable("id") Long hotelRoomId)
            throws HotelRoomNotFoundException {
        return deleteHotelRoomUseCase.execute(hotelRoomId)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .onErrorResume(HotelRoomNotFoundException.class,
                        e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @Data
    public static class CreateHotelRoomRequest{
        @Positive(message = "SerialNumber cannot be empty or negative")
        private Integer serialNumber;
        @Positive
        private BigDecimal price;
        private RoomType roomType;
        @Positive
        private Integer capacity;
    }
}
