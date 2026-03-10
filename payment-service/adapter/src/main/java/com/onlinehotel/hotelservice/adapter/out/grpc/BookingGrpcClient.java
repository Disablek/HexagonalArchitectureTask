package com.onlinehotel.hotelservice.adapter.out.grpc;

import com.onlinehotel.bookingservice.adapter.out.grpc.BookingDetailsRequest;
import com.onlinehotel.bookingservice.adapter.out.grpc.BookingServiceGrpc;
import com.onlinehotel.hotelservice.adapter.mapper.grpc.BookingGrpcMapper;
import com.onlinehotel.hotelservice.application.dto.BookingDetailsDto;
import com.onlinehotel.hotelservice.application.port.out.grpc.BookingServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookingGrpcClient implements BookingServicePort {
    private final BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub;
    private final BookingGrpcMapper bookingGrpcMapper;

    @Override
    public BookingDetailsDto getBookingDetails(Long bookingId) {
        BookingDetailsRequest bookingRequest = BookingDetailsRequest.newBuilder()
                .setBookingId(bookingId)
                .build();
        return bookingGrpcMapper.toDto(
                bookingServiceBlockingStub.getBookingDetails(bookingRequest)
        );
    }
}
