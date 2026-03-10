package com.onlinehotel.bookingservice.adapter.in.grpc;

import com.onlinehotel.bookingservice.adapter.mapper.grpc.BookingGrpcMapper;
import com.onlinehotel.bookingservice.adapter.mapper.grpc.HotelRoomGrpcMapper;
import com.onlinehotel.bookingservice.adapter.out.grpc.*;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.in.GetBookingByIdUseCase;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import com.onlinehotel.bookingservice.model.Booking;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class BookingGrpcService extends BookingServiceGrpc.BookingServiceImplBase {
    private final CreateBookingUseCase  createBookingUseCase;
    private final GetBookingByIdUseCase getBookingByIdUseCase;
    private final HotelRoomServicePort hotelRoomServicePort;
    private final BookingGrpcMapper grpcMapper;
    private final HotelRoomGrpcMapper hotelRoomGrpcMapper;

    @Override
    public void getBookingDetails(BookingDetailsRequest bookingDetailsRequest, StreamObserver<BookingResponse> responseObserver){
        Booking booking = getBookingByIdUseCase.execute(bookingDetailsRequest.getBookingId());
        HotelRoomDetails hotelRoomDetails = hotelRoomGrpcMapper.fromDto(
                hotelRoomServicePort.getHotelDetails(booking.getHotelId(), booking.getHotelRoomId()));
        BookingResponse response = BookingResponse.newBuilder()
                .setBookingId(booking.getId())
                .setStatus(booking.getBookingStatus().toString())
                .setTotalPrice(booking.getTotalPrice().toString())
                .setCheckInDate(booking.getDateRange().getCheckIn().toString())
                .setCheckInDate(booking.getDateRange().getCheckOut().toString())
                .setHotelDetails(hotelRoomDetails)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void processBooking(BookingRequest request, StreamObserver<BookingResponse> responseObserver) {
        try {
            BookingResponse response = grpcMapper.toGrpcResponse(
                                            createBookingUseCase.execute(
                                                grpcMapper.toDomain(request)));
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
