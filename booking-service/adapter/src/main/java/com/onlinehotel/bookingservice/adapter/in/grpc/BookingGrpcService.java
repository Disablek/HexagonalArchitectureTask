package com.onlinehotel.bookingservice.adapter.in.grpc;

import com.onlinehotel.bookingservice.adapter.mapper.grpc.BookingGrpcMapper;
import com.onlinehotel.bookingservice.adapter.out.grpc.BookingRequest;
import com.onlinehotel.bookingservice.adapter.out.grpc.BookingResponse;
import com.onlinehotel.bookingservice.adapter.out.grpc.BookingServiceGrpc;
import com.onlinehotel.bookingservice.application.port.in.CreateBookingUseCase;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@AllArgsConstructor
@GrpcService
public class BookingGrpcService extends BookingServiceGrpc.BookingServiceImplBase {
    private final CreateBookingUseCase  createBookingUseCase;
    private final BookingGrpcMapper grpcMapper;

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
