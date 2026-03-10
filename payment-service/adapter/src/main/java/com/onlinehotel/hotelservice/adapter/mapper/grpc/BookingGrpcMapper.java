package com.onlinehotel.hotelservice.adapter.mapper.grpc;

import com.onlinehotel.bookingservice.adapter.out.grpc.BookingResponse;
import com.onlinehotel.bookingservice.adapter.out.grpc.HotelRoomDetails;
import com.onlinehotel.hotelservice.application.dto.BookingDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface BookingGrpcMapper {
    @Mapping(target = "bookingId", source = "bookingId")
    @Mapping(target = "bookingStatus", source = "status")
    @Mapping(target = "hotelRoomDetailsDto", source = "hotelDetails", qualifiedByName = "roomDetailsToDto")
    @Mapping(target = "checkInDate", source = "checkInDate", qualifiedByName = "stringToLocalDate")
    @Mapping(target = "checkOutDate", source = "checkOutDate", qualifiedByName = "stringToLocalDate")
    BookingDetailsDto toDto(BookingResponse booking);

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr);
    }

    @Named("roomDetailsToDto")
    @Mapping(target = "roomId", source = "roomId")
    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "capacity", source = "capacity")
    @Mapping(target = "roomType", source = "roomType")
    @Mapping(target = "price", source = "price", qualifiedByName = "stringToBigDecimal")
    BookingDetailsDto.HotelRoomDetailsDto roomDetailsToDto(HotelRoomDetails grpc);

    @Named("stringToBigDecimal")
    default BigDecimal stringToBigDecimal(String priceStr) {
        return priceStr != null ? new BigDecimal(priceStr) : BigDecimal.ZERO;
    }

    @Mapping(target = "bookingId", source = "bookingId")
    @Mapping(target = "status", source = "bookingStatus")
    BookingResponse toGrpc(BookingDetailsDto dto);
}
