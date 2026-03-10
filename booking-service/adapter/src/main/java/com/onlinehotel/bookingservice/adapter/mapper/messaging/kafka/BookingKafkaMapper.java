package com.onlinehotel.bookingservice.adapter.mapper.messaging.kafka;

import com.onlinehotel.bookingservice.application.dto.ConfirmBookingDto;
import com.onlinehotel.bookingservice.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingKafkaMapper {

    @Mapping(target = "id", source = "bookingId")
    Booking toDomain(ConfirmBookingDto dto);

    ConfirmBookingDto toDto(Booking payment);
}