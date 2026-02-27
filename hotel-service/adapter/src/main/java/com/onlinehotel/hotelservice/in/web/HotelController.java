package com.onlinehotel.hotelservice.in.web;

import com.onlinehotel.hotelservice.application.port.in.hotel.CreateHotelUseCase;
import com.onlinehotel.hotelservice.application.port.in.hotel.DeleteHotelUseCase;
import com.onlinehotel.hotelservice.application.port.in.hotel.GetHotelUseCase;
import com.onlinehotel.hotelservice.application.port.in.hotel.UpdateHotelUseCase;
import com.onlinehotel.hotelservice.exception.HotelAlreadyExists;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
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

@RestController
@RequestMapping("api/hotel")
@Tag(name = "Hotels", description = "Hotel management operations")
@AllArgsConstructor
public class HotelController {

    private final CreateHotelUseCase createHotelUseCase;
    private final DeleteHotelUseCase deleteHotelUseCase;
    private final GetHotelUseCase getHotelUseCase;
    private final UpdateHotelUseCase updateHotelUseCase;

    @PatchMapping("/{id}")
    @Operation(
            description = "Get test by test-id",
            summary = "Get test summary"
    )
    public ResponseEntity<Hotel> updateHotel(@NotNull @PathVariable("id") Long id,
                                             @Valid @RequestBody UpdateHotelUseCase.UpdateHotelCommand command)
            throws HotelNotFoundException {
        Hotel updatedHotel = updateHotelUseCase.execute(id ,command);
        return ResponseEntity.ok(updatedHotel);
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Get hotel by hotel-id",
            summary = "Get hotel parameters"
    )
    public ResponseEntity<Hotel> getHotelById(@NotNull  @PathVariable("id") Long id) throws HotelNotFoundException {
        Hotel hotel = getHotelUseCase.execute(id)
                .orElseThrow(() -> new HotelNotFoundException("User with id " + id + " not found"));
        return ResponseEntity.ok().body(hotel);
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
        CreateHotelUseCase.CreateHotelCommand command =
                new CreateHotelUseCase.CreateHotelCommand(request.getHotelName(),  request.getHotelAddress());
        Hotel createdHotel = createHotelUseCase.execute(command);
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
