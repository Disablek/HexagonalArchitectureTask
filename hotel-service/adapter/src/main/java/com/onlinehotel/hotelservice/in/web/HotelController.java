package com.onlinehotel.hotelservice.in.web;

import com.onlinehotel.hotelservice.application.port.in.hotel.*;
import com.onlinehotel.hotelservice.exception.HotelAlreadyExists;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.out.persistence.jpa.mapper.HotelMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public ResponseEntity<Set<Hotel>> getAllHotels(){
        return ResponseEntity.ok(getAllHotelsUseCase.execute());
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get hotel by hotel-id",
            summary = "Get hotel parameters"
    )
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                getHotelByIdUseCase.execute(id));
    }

    @PatchMapping("/{id}")
    @Operation(
            description = "Patch hotel by hotel-id",
            summary = "Patch hotel parameters"
    )
    public ResponseEntity<Hotel> updateHotel(@NotNull @PathVariable("id") Long id,
                                             @Valid @RequestBody UpdateHotelUseCase.UpdateHotelCommand command)
            throws HotelNotFoundException {
        Hotel updatedHotel = updateHotelUseCase.execute(id ,command);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Delete hotel by hotel-id (HotelRoom - cascadeType)",
            summary = "Delete hotel"
    )
    public ResponseEntity<?> deleteHotel(@NotNull @PathVariable("id") Long id) throws HotelNotFoundException {
        deleteHotelUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @Operation(summary = "Create new hotel")
    public ResponseEntity<Hotel> createHotel(@Valid @RequestBody CreateHotelRequest request) throws HotelAlreadyExists {
        Hotel createdHotel = createHotelUseCase.execute(hotelMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHotel);
    }

    @Data
    public static class CreateHotelRequest{
        @NotEmpty(message = "Hotelname must not be empty")
        private String hotelName;

        @NotEmpty(message = "Hoteladdress must not be empty")
        private String hotelAddress;
    }
}
