package com.onlinehotel.bookingservice.adapter.mapper.jpa;

import com.onlinehotel.bookingservice.adapter.out.persistence.jpa.model.BookingJpaEntity;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.model.Booking;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-12T12:59:13+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.3.1.jar, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class BookingJpaMapperImpl implements BookingJpaMapper {

    @Override
    public Set<Booking> toDomain(Set<BookingJpaEntity> bookingJpaEntities) {
        if ( bookingJpaEntities == null ) {
            return null;
        }

        Set<Booking> set = LinkedHashSet.newLinkedHashSet( bookingJpaEntities.size() );
        for ( BookingJpaEntity bookingJpaEntity : bookingJpaEntities ) {
            set.add( toDomain( bookingJpaEntity ) );
        }

        return set;
    }

    @Override
    public Booking toDomain(CreateBookingUseCase.CreateBookingCommand createBookingCommand) {
        if ( createBookingCommand == null ) {
            return null;
        }

        Booking booking = new Booking();

        booking.setHotelId( createBookingCommand.hotelId() );
        booking.setHotelRoomId( createBookingCommand.hotelRoomId() );
        booking.setDateRange( createBookingCommand.dateRange() );

        return booking;
    }

    @Override
    public Booking toDomain(BookingJpaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Booking booking = new Booking();

        booking.setId( entity.getId() );
        booking.setHotelId( entity.getHotelId() );
        booking.setHotelRoomId( entity.getHotelRoomId() );
        booking.setDateRange( map( entity.getDateRange() ) );
        booking.setBookingStatus( entity.getBookingStatus() );
        booking.setTotalPrice( entity.getTotalPrice() );

        return booking;
    }

    @Override
    public BookingJpaEntity toJpaEntity(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingJpaEntity bookingJpaEntity = new BookingJpaEntity();

        bookingJpaEntity.setId( booking.getId() );
        bookingJpaEntity.setTotalPrice( booking.getTotalPrice() );
        bookingJpaEntity.setHotelId( booking.getHotelId() );
        bookingJpaEntity.setHotelRoomId( booking.getHotelRoomId() );
        bookingJpaEntity.setDateRange( map( booking.getDateRange() ) );
        bookingJpaEntity.setBookingStatus( booking.getBookingStatus() );

        return bookingJpaEntity;
    }
}
