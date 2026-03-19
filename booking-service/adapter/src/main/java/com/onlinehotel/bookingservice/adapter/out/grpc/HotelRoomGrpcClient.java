package com.onlinehotel.bookingservice.adapter.out.grpc;

import com.onlinehotel.bookingservice.adapter.mapper.grpc.HotelRoomGrpcMapper;
import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;

import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomRequest;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
public class HotelRoomGrpcClient implements HotelRoomServicePort {

    private final HotelRoomServiceGrpc.HotelRoomServiceBlockingStub blockingStub;
    private final HotelRoomGrpcMapper hotelRoomGrpcMapper;

    public HotelRoomGrpcClient(
            HotelRoomServiceGrpc.HotelRoomServiceBlockingStub blockingStub,
            HotelRoomGrpcMapper hotelRoomGrpcMapper) {
        this.blockingStub = blockingStub;
        this.hotelRoomGrpcMapper = hotelRoomGrpcMapper;
    }

    @CircuitBreaker(name="HOTEL-SERVICE", fallbackMethod = "getDefaultHotelRoom")
    @Override
    public Mono<HotelRoomDetailsDto> getHotelDetails(Long hotelId, Long roomId) {
        HotelRoomRequest request = HotelRoomRequest.newBuilder()
                .setHotelId(hotelId)
                .setRoomId(roomId)
                .build();
        return Mono.just(hotelRoomGrpcMapper.toDto(
                blockingStub.getHotelRoomDetails(request)));
    }

    @Override
    public boolean checkAvailability(Long hotelId, Long roomId, LocalDate checkIn, LocalDate checkOut) {
        return false;
    }

}
