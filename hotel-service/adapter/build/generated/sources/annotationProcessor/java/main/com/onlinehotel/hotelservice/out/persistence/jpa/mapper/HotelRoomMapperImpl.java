package com.onlinehotel.hotelservice.out.persistence.jpa.mapper;

import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.model.RoomType;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelRoomJpaEntity;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-27T16:00:43+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.3.1.jar, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class HotelRoomMapperImpl implements HotelRoomMapper {

    @Override
    public Set<HotelRoomJpaEntity> roomsToJpa(Set<HotelRoom> rooms) {
        if ( rooms == null ) {
            return null;
        }

        Set<HotelRoomJpaEntity> set = LinkedHashSet.newLinkedHashSet( rooms.size() );
        for ( HotelRoom hotelRoom : rooms ) {
            set.add( toJpaEntity( hotelRoom ) );
        }

        return set;
    }

    @Override
    public Set<HotelRoom> roomsToDomain(Set<HotelRoomJpaEntity> rooms) {
        if ( rooms == null ) {
            return null;
        }

        Set<HotelRoom> set = LinkedHashSet.newLinkedHashSet( rooms.size() );
        for ( HotelRoomJpaEntity hotelRoomJpaEntity : rooms ) {
            set.add( toDomain( hotelRoomJpaEntity ) );
        }

        return set;
    }

    @Override
    public HotelRoomJpaEntity toJpaEntity(HotelRoom domain) {
        if ( domain == null ) {
            return null;
        }

        HotelRoomJpaEntity hotelRoomJpaEntity = new HotelRoomJpaEntity();

        hotelRoomJpaEntity.setCapacity( domain.capacity() );
        hotelRoomJpaEntity.setSerialNumber( domain.serialNumber() );
        hotelRoomJpaEntity.setPrice( domain.price() );
        hotelRoomJpaEntity.setRoomType( domain.roomType() );

        return hotelRoomJpaEntity;
    }

    @Override
    public HotelRoom toDomain(HotelRoomJpaEntity jpa) {
        if ( jpa == null ) {
            return null;
        }

        Long id = null;
        Integer serialNumber = null;
        Integer capacity = null;
        BigDecimal price = null;
        RoomType roomType = null;

        id = jpa.getId();
        serialNumber = jpa.getSerialNumber();
        capacity = jpa.getCapacity();
        price = jpa.getPrice();
        roomType = jpa.getRoomType();

        Long hotelId = jpa.getHotel().getId();

        HotelRoom hotelRoom = new HotelRoom( id, serialNumber, capacity, hotelId, price, roomType );

        return hotelRoom;
    }
}
