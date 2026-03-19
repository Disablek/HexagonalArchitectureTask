package com.onlinehotel.bookingservice.adapter.in.grpc;

import com.onlinehotel.bookingservice.adapter.mapper.grpc.BookingGrpcMapper;
import com.onlinehotel.bookingservice.adapter.mapper.grpc.HotelRoomGrpcMapper;
import com.onlinehotel.bookingservice.adapter.out.grpc.*;
import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import com.onlinehotel.bookingservice.application.port.in.GetBookingByIdUseCase;
import com.onlinehotel.bookingservice.application.port.out.grpc.HotelRoomServicePort;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;

@GrpcService
@AllArgsConstructor
public class BookingGrpcService extends BookingServiceGrpc.BookingServiceImplBase {
    private final CreateBookingUseCase createBookingUseCase;
    private final GetBookingByIdUseCase getBookingByIdUseCase;
    private final HotelRoomServicePort hotelRoomServicePort;
    private final BookingGrpcMapper grpcMapper;
    private final HotelRoomGrpcMapper hotelRoomGrpcMapper;

    @Override
    public void getBookingDetails(BookingDetailsRequest request,
                                  StreamObserver<BookingResponse> responseObserver) {

        getBookingByIdUseCase.execute(request.getBookingId())
                .flatMap(booking -> {
                    HotelRoomDetailsDto details = hotelRoomServicePort
                            .getHotelDetails(booking.getHotelId(), booking.getHotelRoomId())
                            .block();

                    HotelRoomDetails hotelRoomDetails = hotelRoomGrpcMapper.fromDto(details);

                    BookingResponse response = BookingResponse.newBuilder()
                            .setBookingId(booking.getId())
                            .setStatus(booking.getBookingStatus().toString())
                            .setTotalPrice(booking.getTotalPrice().toString())
                            .setCheckInDate(booking.getCheckIn().toString())
                            .setCheckOutDate(booking.getCheckOut().toString())
                            .setHotelDetails(hotelRoomDetails)
                            .build();

                    return Mono.just(response);
                })
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        responseObserver::onError,
                        () -> {
                            responseObserver.onError(
                                    Status.NOT_FOUND.asRuntimeException());
                        }
                );
    }

    @Override
    public void processBooking(BookingRequest request,
                               StreamObserver<BookingResponse> responseObserver) {
        createBookingUseCase.execute(grpcMapper.toDomain(request))
                .map(grpcMapper::toGrpcResponse)
                .subscribe(
                        response -> {
                            responseObserver.onNext(response);
                            responseObserver.onCompleted();
                        },
                        responseObserver::onError,
                        () -> responseObserver.onError(Status.INTERNAL.asRuntimeException())
                );
    }
}
