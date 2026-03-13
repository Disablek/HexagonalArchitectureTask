package com.onlinehotel.hotelservice.out.persistence.jpa.mapper;

import com.onlinehotel.hotelservice.in.web.HotelRoomController;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelRoomJpaEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-13T12:38:02+0300",
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

        hotelRoomJpaEntity.setCapacity( domain.getCapacity() );
        hotelRoomJpaEntity.setSerialNumber( domain.getSerialNumber() );
        hotelRoomJpaEntity.setPrice( domain.getPrice() );
        hotelRoomJpaEntity.setRoomType( domain.getRoomType() );

        return hotelRoomJpaEntity;
    }

    @Override
    public HotelRoom toDomain(HotelRoomJpaEntity jpa) {
        if ( jpa == null ) {
            return null;
        }

        HotelRoom hotelRoom = new HotelRoom();

        hotelRoom.setRoomType( jpa.getRoomType() );
        hotelRoom.setPrice( jpa.getPrice() );
        hotelRoom.setCapacity( jpa.getCapacity() );
        hotelRoom.setSerialNumber( jpa.getSerialNumber() );
        hotelRoom.setId( jpa.getId() );

        hotelRoom.setHotelId( jpa.getHotel().getId() );

        return hotelRoom;
    }

    @Override
    public HotelRoom toDomain(HotelRoomController.CreateHotelRoomRequest createHotelRoomRequest) {
        if ( createHotelRoomRequest == null ) {
            return null;
        }

        HotelRoom hotelRoom = new HotelRoom();

        hotelRoom.setRoomType( createHotelRoomRequest.getRoomType() );
        hotelRoom.setPrice( createHotelRoomRequest.getPrice() );
        hotelRoom.setCapacity( createHotelRoomRequest.getCapacity() );
        hotelRoom.setSerialNumber( createHotelRoomRequest.getSerialNumber() );

        return hotelRoom;
    }

    @Override
    public void updateEntityFromDomain(HotelRoomJpaEntity entity, HotelRoom domain) {
        if ( domain == null ) {
            return;
        }

        entity.setSerialNumber( domain.getSerialNumber() );
        entity.setCapacity( domain.getCapacity() );
        entity.setPrice( domain.getPrice() );
        entity.setRoomType( domain.getRoomType() );
    }
}
