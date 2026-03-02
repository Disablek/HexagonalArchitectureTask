package com.onlinehotel.hotelservice.out.persistence.jpa.mapper;

import com.onlinehotel.hotelservice.application.port.in.hotel.CreateHotelUseCase;
import com.onlinehotel.hotelservice.in.web.HotelController;
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

    @Named("toJpaEntity")
    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toJpaRooms")
    HotelJpaEntity toJpaEntity(Hotel domain);

    @Named("toDomain")
    @Mapping(target = "rooms", source = "rooms", qualifiedByName = "toDomainRooms")
    Hotel toDomain(HotelJpaEntity jpa);

    @Named("toJpaCommand")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "hotelName")
    @Mapping(target = "address", source = "hotelAddress")
    @Mapping(target = "rooms", ignore = true)
    HotelJpaEntity toJpaEntity(CreateHotelUseCase.CreateHotelCommand command);

    @Named("toDomainRequest")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "hotelName")
    @Mapping(target = "address", source = "hotelAddress")
    @Mapping(target = "rooms", ignore = true)
    Hotel toDomain(HotelController.CreateHotelRequest createHotelRequest);
}
