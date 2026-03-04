package com.onlinehotel.bookingservice.adapter.out.persistence.jpa.mapper;

import com.onlinehotel.bookingservice.adapter.out.persistence.jpa.model.BookingJpaEntity;
import com.onlinehotel.bookingservice.adapter.out.persistence.jpa.model.DateRangeJpaEntity;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.DateRange;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface BookingJpaMapper {

    Set<Booking> toDomain(Set<BookingJpaEntity> bookingJpaEntities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "bookingStatus", ignore = true)
    Booking toDomain(CreateBookingUseCase.CreateBookingCommand createBookingCommand);

    Booking toDomain(BookingJpaEntity entity);

    default DateRange map(DateRangeJpaEntity dateRange) {
        if (dateRange == null) return null;
        return new DateRange(dateRange.getCheckIn(), dateRange.getCheckOut());
    }

    default DateRangeJpaEntity map(DateRange dateRange) {
        if (dateRange == null) return null;
        return new DateRangeJpaEntity (dateRange.getCheckIn(), dateRange.getCheckOut());
    }

    @Mapping(target = "createdAt", ignore = true)
    BookingJpaEntity toJpaEntity(Booking booking);
}



