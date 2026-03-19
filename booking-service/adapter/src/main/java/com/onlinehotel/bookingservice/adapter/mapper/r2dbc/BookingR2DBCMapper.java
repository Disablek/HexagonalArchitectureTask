package com.onlinehotel.bookingservice.adapter.mapper.r2dbc;

import com.onlinehotel.bookingservice.adapter.out.persistence.r2dbc.model.BookingJpaEntity;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface BookingR2DBCMapper {

    Set<Booking> toDomain(Set<BookingJpaEntity> bookingJpaEntities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "bookingStatus", ignore = true)
    Booking toDomain(CreateBookingUseCase.CreateBookingCommand createBookingCommand);

    Booking toDomain(BookingJpaEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    BookingJpaEntity toJpaEntity(Booking booking);

}



