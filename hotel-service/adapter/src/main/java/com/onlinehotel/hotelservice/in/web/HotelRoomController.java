package com.onlinehotel.hotelservice.in.web;

import com.onlinehotel.hotelservice.application.port.in.hotelroom.*;
import com.onlinehotel.hotelservice.exception.DuplicateSerialNumberException;
import com.onlinehotel.hotelservice.exception.HotelRoomNotFoundException;
import com.onlinehotel.hotelservice.exception.InvalidArgumentException;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.model.RoomType;
import com.onlinehotel.hotelservice.out.persistence.jpa.mapper.HotelRoomMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

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
    public ResponseEntity<Set<HotelRoom>> getAllHotelRoomsByHotelId(@PathVariable("hotelId") Long hotelId)
            throws HotelRoomNotFoundException {
        return ResponseEntity.ok(getAllHotelRoomsByHotelIdUseCase.execute(hotelId));
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get hotelRoom by hotelRoom-id",
            summary = "Get hotelRoom parameters"
    )
    public ResponseEntity<HotelRoom> getHotelRoomById(@NotNull @PathVariable("id") Long hotelRoomId)
            throws HotelRoomNotFoundException {
        return ResponseEntity.ok().body(getHotelRoomByIdUseCase.execute(hotelRoomId));
    }

    @GetMapping
    @Operation(
            description = "Get all hotelRooms",
            summary = "Get all hotelRooms"
    )
    public ResponseEntity<Set<HotelRoom>> getAllHotelRooms(){
        return ResponseEntity.ok().body(
                getAllHotelRoomsUseCase.execute());
    }

    @PostMapping("/{hotelId}/rooms")
    @Operation(summary = "Create hotelRoom in hotel")
    public ResponseEntity<HotelRoom> createHotelRoom(
                                            @Valid @RequestBody CreateHotelRoomRequest createHotelRoomRequest,
                                            @RequestParam("hotelId") Long hotelId)
                                            throws DuplicateSerialNumberException, InvalidArgumentException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                createHotelRoomUseCase.execute(
                        hotelId,
                        hotelRoomMapper.toDomain(createHotelRoomRequest)
        ));
    }

    @PatchMapping("/{id}")
    @Operation(
            description = "Patch hotelRoom by hotelRoom-id",
            summary = "Patch hotelRoom parameters"
    )
    public ResponseEntity<HotelRoom> updateHotelRoomById(@NotNull @PathVariable("id") Long hotelRoomId,
                                                         @Valid @RequestBody UpdateHotelRoomUseCase.UpdateHotelRoomCommand command)
            throws HotelRoomNotFoundException {
        HotelRoom hotelRoom = updateHotelRoomUseCase.execute(hotelRoomId, command);
        return ResponseEntity.ok().body(hotelRoom);
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete hotelRoom by hotelRoom-id",
            summary = "Delete hotelRoom"
    )
    public ResponseEntity<?> deleteHotelRoomById(@NotNull @PathVariable("id") Long hotelRoomId)
            throws HotelRoomNotFoundException {
        deleteHotelRoomUseCase.execute(hotelRoomId);
        return ResponseEntity.ok().build();
    }

    @Data
    public static class CreateHotelRoomRequest{
        @Positive(message = "SerialNumber cannot be empty or negative")
        private Integer serialNumber;
        @Positive
        private BigDecimal price;
        @Enumerated(EnumType.STRING)
        private RoomType roomType;
        @Positive
        private Integer capacity;
    }
}
