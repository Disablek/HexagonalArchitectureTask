package com.onlinehotel.hotelservice.application.service;

import com.onlinehotel.hotelservice.exception.HotelNotFoundException;
import com.onlinehotel.hotelservice.exception.InvalidArgumentException;
import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.application.port.in.hotel.CreateHotelUseCase;
import com.onlinehotel.hotelservice.application.port.out.persistence.HotelRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateHotelService implements CreateHotelUseCase {

    private final HotelRepositoryPort hotelRepository;

    public CreateHotelService(HotelRepositoryPort hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public Hotel execute(CreateHotelCommand command) throws HotelNotFoundException {
        validateHotelCreation(command);

        Hotel hotel = new Hotel(
                null,
                command.hotelName(),
                command.hotelAddress(),
                null
        );

        return hotelRepository.save(hotel);
    }

    public void validateHotelCreation(CreateHotelCommand command) throws InvalidArgumentException {
        if (command.hotelAddress().isEmpty())
            throw new InvalidArgumentException("Hotel address cannot be empty", "hotelAddress");
        if (command.hotelName().isEmpty())
            throw new InvalidArgumentException("Hotel name cannot be empty", "hotelName");
    }
}
