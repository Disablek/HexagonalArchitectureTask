package com.onlinehotel.bookingservice.adapter.mapper.grpc;

import com.onlinehotel.bookingservice.adapter.out.grpc.*;
import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.bookingservice.model.Booking;
import com.onlinehotel.bookingservice.model.BookingStatus;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomDetails;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Mapper(
        componentModel = "spring",
        imports = {LocalDate.class, BigDecimal.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface BookingGrpcMapper {

    @Mapping(target = "id", source = "bookingId")
    @Mapping(target = "hotelId", source = "hotelId")
    @Mapping(target = "hotelRoomId", source = "roomId")
    @Mapping(target = "dateRange.checkIn", source = "checkInDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "dateRange.checkOut", source = "checkOutDate", dateFormat = "yyyy-MM-dd")
    Booking toDomain(BookingRequest grpcRequest);

    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "hotelId", source = "hotelId")
    @Mapping(target = "roomId", source = "hotelRoomId")
    @Mapping(target = "checkInDate", source = "dateRange.checkIn")
    @Mapping(target = "checkOutDate", source = "dateRange.checkOut")
    BookingRequest toGrpc(Booking domain);

    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "status", source = "bookingStatus", qualifiedByName = "statusToString")
    BookingResponse toGrpcResponse(Booking domain);

    @Mapping(target = "serialNumber", source = "serialNumber")
    @Mapping(target = "capacity", source = "capacity")
    @Mapping(target = "roomType", source = "roomType")
    @Mapping(target = "price", source = "price", qualifiedByName = "stringToBigDecimal")
    HotelRoomDetailsDto toDto(HotelRoomDetails grpc);

    @Named("statusToString")
    default String statusToString(BookingStatus status) {
        return status != null ? status.name() : "PENDING";
    }

    @Named("stringToBigDecimal")
    default BigDecimal stringToBigDecimal(String priceStr) {
        return priceStr != null ? new BigDecimal(priceStr) : BigDecimal.ZERO;
    }
}
