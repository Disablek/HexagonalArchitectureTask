package com.onlinehotel.hotelservice.in.grpc;

import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomDetails;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomRequest;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import com.onlinehotel.hotelservice.adapter.in.grpc.RoomType;
import com.onlinehotel.hotelservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.hotelservice.application.port.in.hotelroom.GetHotelRoomDetailsUseCase;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;


@GrpcService
@AllArgsConstructor
public class HotelRoomGrpcService extends HotelRoomServiceGrpc.HotelRoomServiceImplBase {
    private final GetHotelRoomDetailsUseCase getHotelRoomDetailsUseCase;

    @Override
    public void getHotelRoomDetails(HotelRoomRequest request, StreamObserver<HotelRoomDetails> response){

        getHotelRoomDetailsUseCase.details(request.getRoomId())
                .flatMap( hotelRoomDetailsDto -> {
                    RoomType protoRoomType = RoomType.valueOf(hotelRoomDetailsDto.roomType().name());
                    HotelRoomDetails responseDto = HotelRoomDetails.newBuilder()
                            .setRoomType(protoRoomType)
                            .setCapacity(hotelRoomDetailsDto.capacity())
                            .setSerialNumber(hotelRoomDetailsDto.serialNumber())
                            .setPrice(hotelRoomDetailsDto.price().toString())
                            .build();
                    return Mono.just(responseDto);
                })
                .subscribe(
                        responseDto -> {
                            response.onNext(responseDto);
                            response.onCompleted();
                        },
                        response::onError,
                        () -> {
                            response.onError(
                                    Status.NOT_FOUND.asRuntimeException());
                        }
                );
    }
}

