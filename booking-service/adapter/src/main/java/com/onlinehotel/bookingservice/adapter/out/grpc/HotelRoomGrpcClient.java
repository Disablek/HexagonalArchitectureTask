package com.onlinehotel.bookingservice.adapter.out.grpc;

import com.onlinehotel.bookingservice.adapter.mapper.grpc.HotelRoomGrpcMapper;
import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;

import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomRequest;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class HotelRoomGrpcClient implements HotelRoomServicePort {

    private final HotelRoomServiceGrpc.HotelRoomServiceBlockingStub blockingStub;
    private final HotelRoomGrpcMapper hotelRoomGrpcMapper;

    @Override
    public HotelRoomDetailsDto getHotelDetails(Long hotelId, Long roomId) {
        HotelRoomRequest request = HotelRoomRequest.newBuilder()
                .setHotelId(hotelId)
                .setRoomId(roomId)
                .build();
        return hotelRoomGrpcMapper.toDto(
                blockingStub.getHotelRoomDetails(request));
    }

    @Override
    public boolean checkAvailability(Long hotelId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
        return false;
    }

}
