package com.onlinehotel.bookingservice.adapter.in.grpc.mapper;

import com.onlinehotel.bookingservice.adapter.out.grpc.BookingRequest;
import com.onlinehotel.bookingservice.adapter.out.grpc.BookingResponse;
import com.onlinehotel.bookingservice.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = {LocalDate.class})
public interface BookingGRPCMapper {

    @Mapping(target = "id", source = "bookingId")
    @Mapping(target = "hotelId", source = "hotelId")
    @Mapping(target = "roomId", source = "roomId")
    @Mapping(target = "checkInDate", source = "checkInDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "checkOutDate", source = "checkOutDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "guestsCount", source = "guestsCount")
    Booking toDomain(BookingRequest grpcRequest);

    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "hotelId", source = "hotelId")
    @Mapping(target = "roomId", source = "roomId")
    @Mapping(target = "checkInDate", source = "checkInDate")
    @Mapping(target = "checkOutDate", source = "checkOutDate")
    @Mapping(target = "guestsCount", source = "guestsCount")
    BookingRequest toGrpc(Booking domain);

    @Mapping(target = "bookingId", source = "id")
    @Mapping(target = "status", source = "status.name")
    @Mapping(target = "hotelDetails", source = "hotel")
    BookingResponse toGrpcResponse(Booking domain);

    @Mapping(target = "id", source = "bookingId")
    @Mapping(target = "status", source = "status")
    Booking fromGrpcResponse(BookingResponse grpcResponse);
}

