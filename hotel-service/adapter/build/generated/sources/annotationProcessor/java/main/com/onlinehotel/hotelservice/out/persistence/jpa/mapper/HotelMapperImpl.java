package com.onlinehotel.hotelservice.out.persistence.jpa.mapper;

import com.onlinehotel.hotelservice.application.port.in.hotel.CreateHotelUseCase;
import com.onlinehotel.hotelservice.in.web.HotelController;
import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelJpaEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-13T12:38:02+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.3.1.jar, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class HotelMapperImpl implements HotelMapper {

    @Autowired
    private HotelRoomMapper hotelRoomMapper;

    @Override
    public Set<HotelJpaEntity> toJpaSet(Set<Hotel> domain) {
        if ( domain == null ) {
            return null;
        }

        Set<HotelJpaEntity> set = LinkedHashSet.newLinkedHashSet( domain.size() );
        for ( Hotel hotel : domain ) {
            set.add( toJpaEntity( hotel ) );
        }

        return set;
    }

    @Override
    public Set<Hotel> toDomainSet(Set<HotelJpaEntity> domain) {
        if ( domain == null ) {
            return null;
        }

        Set<Hotel> set = LinkedHashSet.newLinkedHashSet( domain.size() );
        for ( HotelJpaEntity hotelJpaEntity : domain ) {
            set.add( toDomain( hotelJpaEntity ) );
        }

        return set;
    }

    @Override
    public HotelJpaEntity toJpaEntity(Hotel domain) {
        if ( domain == null ) {
            return null;
        }

        HotelJpaEntity hotelJpaEntity = new HotelJpaEntity();

        hotelJpaEntity.setRooms( hotelRoomMapper.roomsToJpa( domain.getRooms() ) );
        hotelJpaEntity.setId( domain.getId() );
        hotelJpaEntity.setName( domain.getName() );
        hotelJpaEntity.setAddress( domain.getAddress() );

        return hotelJpaEntity;
    }

    @Override
    public Hotel toDomain(HotelJpaEntity jpa) {
        if ( jpa == null ) {
            return null;
        }

        Hotel hotel = new Hotel();

        hotel.setRooms( hotelRoomMapper.roomsToDomain( jpa.getRooms() ) );
        hotel.setId( jpa.getId() );
        hotel.setName( jpa.getName() );
        hotel.setAddress( jpa.getAddress() );

        return hotel;
    }

    @Override
    public HotelJpaEntity toJpaEntity(CreateHotelUseCase.CreateHotelCommand command) {
        if ( command == null ) {
            return null;
        }

        HotelJpaEntity hotelJpaEntity = new HotelJpaEntity();

        hotelJpaEntity.setName( command.hotelName() );
        hotelJpaEntity.setAddress( command.hotelAddress() );

        return hotelJpaEntity;
    }

    @Override
    public Hotel toDomain(HotelController.CreateHotelRequest createHotelRequest) {
        if ( createHotelRequest == null ) {
            return null;
        }

        Hotel hotel = new Hotel();

        hotel.setName( createHotelRequest.getHotelName() );
        hotel.setAddress( createHotelRequest.getHotelAddress() );

        return hotel;
    }
}
