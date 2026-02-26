package com.onlinehotel.hotelservice.in.web;

import com.onlinehotel.hotelservice.application.port.in.hotel.CreateHotelUseCase;
import com.onlinehotel.hotelservice.application.port.in.hotel.DeleteHotelUseCase;
import com.onlinehotel.hotelservice.application.port.in.hotel.GetHotelUseCase;
import com.onlinehotel.hotelservice.application.port.in.hotel.UpdateHotelUseCase;
import com.onlinehotel.hotelservice.exception.HotelAlreadyExists;
import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.model.Hotel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
@AllArgsConstructor
public class HotelController {

    private final CreateHotelUseCase createHotelUseCase;
    private final DeleteHotelUseCase deleteHotelUseCase;
    private final GetHotelUseCase getHotelUseCase;
    private final UpdateHotelUseCase updateHotelUseCase;

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hotel> updateHotel(@NotNull @PathVariable Long id,@Valid @RequestBody Hotel hotel) throws HotelNotFoundException {
        // TODO: Замапить id и hotel в command
        Hotel updatedHotel = updateHotelUseCase.execute(id, hotel);
        return ResponseEntity.ok(updatedHotel);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Hotel> getHotelById(@NotNull @PathVariable Long id) throws HotelNotFoundException {
        Hotel hotel = getHotelUseCase.execute(id)
                .orElseThrow(() -> new HotelNotFoundException("User with id " + id + " not found"));
        return ResponseEntity.ok().body(hotel);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteHotel(@NotNull @PathVariable Long id) throws HotelNotFoundException {
        deleteHotelUseCase.execute(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
