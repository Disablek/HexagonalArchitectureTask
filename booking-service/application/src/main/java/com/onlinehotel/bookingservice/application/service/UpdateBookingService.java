package com.onlinehotel.bookingservice.application.service;

import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.bookingservice.application.port.in.UpdateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.bookingservice.application.port.out.persistence.BookingRepositoryPort;
import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.BookingStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class UpdateBookingService implements UpdateBookingUseCase {
    private final BookingRepositoryPort bookingRepositoryPort;
    private final HotelRoomServicePort hotelRoomServicePort;

    public UpdateBookingService(BookingRepositoryPort bookingRepositoryPort, HotelRoomServicePort hotelRoomServicePort) {
        this.bookingRepositoryPort = bookingRepositoryPort;
        this.hotelRoomServicePort = hotelRoomServicePort;
    }

    @Override
    public Booking execute(Long id, UpdateBookingCommand command) {
        Booking booking = bookingRepositoryPort.findById(id);
        if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
            throw new RuntimeException("Booking is already confirmed");
        }

        updatePartially(booking, command);
        HotelRoomDetailsDto hotelRoomDetailsDto = hotelRoomServicePort.getHotelDetails(booking.getHotelId(), booking.getHotelRoomId());

        BigDecimal totalPrice = hotelRoomDetailsDto.price()
                .multiply(BigDecimal.valueOf(booking.getDateRange().nights()));

        booking.setTotalPrice(totalPrice);

        return bookingRepositoryPort.save(booking);
    }

    void updatePartially(Booking booking, UpdateBookingCommand command) {
        if (command.hotelRoomId().isPresent())
            booking.setHotelRoomId(command.hotelRoomId().get());
        if (command.dateRange().isPresent())
            booking.setDateRange(command.dateRange().get());
    }
}
