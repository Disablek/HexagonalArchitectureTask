package com.onlinehotel.hotelservice.out.persistence.jpa.mapper;

import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelRoomJpaEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface HotelRoomMapper {

    @Named("toJpaRooms")
    @IterableMapping(qualifiedByName = "toJpaRoom")
    Set<HotelRoomJpaEntity> roomsToJpa(Set<HotelRoom> rooms);

    @Named("toDomainRooms")
    @IterableMapping(qualifiedByName = "toDomainRoom")
    Set<HotelRoom> roomsToDomain(Set<HotelRoomJpaEntity> rooms);

    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "id", ignore = true)
    HotelRoomJpaEntity toJpaEntity(HotelRoom domain);

    @Mapping(target = "hotelId", expression = "java(jpa.getHotel().getId())")
    HotelRoom toDomain(HotelRoomJpaEntity jpa);
}

