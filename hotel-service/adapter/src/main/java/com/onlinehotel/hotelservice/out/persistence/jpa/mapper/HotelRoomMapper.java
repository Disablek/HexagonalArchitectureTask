package com.onlinehotel.hotelservice.out.persistence.jpa.mapper;

import com.onlinehotel.hotelservice.in.web.HotelRoomController;
import com.onlinehotel.hotelservice.model.HotelRoom;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelRoomJpaEntity;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface HotelRoomMapper {
    // TODO : маппер для createHotelCommand?
    @Named("toJpaRooms")
    @IterableMapping(qualifiedByName = "toJpaRoom")
    Set<HotelRoomJpaEntity> roomsToJpa(Set<HotelRoom> rooms);

    @Named("toDomainRooms")
    @IterableMapping(qualifiedByName = "toDomainRoom")
    Set<HotelRoom> roomsToDomain(Set<HotelRoomJpaEntity> rooms);

    @Named("toJpaRoom")
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "id", ignore = true)
    HotelRoomJpaEntity toJpaEntity(HotelRoom domain);

    @Named("toDomainRoom")
    @Mapping(target = "hotelId", expression = "java(jpa.getHotel().getId())")
    HotelRoom toDomain(HotelRoomJpaEntity jpa);

    @Named("toDomainRequest")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotelId", ignore = true)
    HotelRoom toDomain(HotelRoomController.CreateHotelRoomRequest createHotelRoomRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "capacity", source = "capacity")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "roomType", source = "roomType")
    void updateEntityFromDomain(@MappingTarget HotelRoomJpaEntity entity, HotelRoom domain);
}

