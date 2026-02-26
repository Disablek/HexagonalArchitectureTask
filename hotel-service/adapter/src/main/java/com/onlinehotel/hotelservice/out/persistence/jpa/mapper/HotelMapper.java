package com.onlinehotel.hotelservice.out.persistence.jpa.mapper;

import com.onlinehotel.hotelservice.model.Hotel;
import com.onlinehotel.hotelservice.out.persistence.jpa.model.HotelJpaEntity;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {HotelRoomMapper.class})
public interface HotelMapper {

    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toJpaRooms")
    @IterableMapping(qualifiedByName = "toJpaEntity")
    Set<HotelJpaEntity> toJpaSet(Set<Hotel> domain);

    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toDomainRooms")
    @IterableMapping(qualifiedByName = "toDomain")
    Set<Hotel> toDomainSet(Set<HotelJpaEntity> domain);

    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toJpaRooms")
    HotelJpaEntity toJpaEntity(Hotel domain);

    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toDomainRooms")
    Hotel toDomain(HotelJpaEntity jpa);
}
