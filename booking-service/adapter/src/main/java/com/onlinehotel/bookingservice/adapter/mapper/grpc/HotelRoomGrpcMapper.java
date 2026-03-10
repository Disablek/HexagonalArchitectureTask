package com.onlinehotel.bookingservice.adapter.mapper.grpc;

import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelRoomGrpcMapper {
    HotelRoomDetailsDto toDto(HotelRoomDetails grpc);

    com.onlinehotel.bookingservice.adapter.out.grpc.HotelRoomDetails fromDto(HotelRoomDetailsDto grpc);
}
