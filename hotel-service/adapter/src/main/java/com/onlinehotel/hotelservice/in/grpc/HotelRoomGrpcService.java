package com.onlinehotel.hotelservice.in.grpc;

import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomDetails;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomRequest;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import com.onlinehotel.hotelservice.adapter.in.grpc.RoomType;
import com.onlinehotel.hotelservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.hotelservice.application.port.in.hotelroom.GetHotelRoomDetailsUseCase;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;


@GrpcService
@AllArgsConstructor
public class HotelRoomGrpcService extends HotelRoomServiceGrpc.HotelRoomServiceImplBase {
    private final GetHotelRoomDetailsUseCase getHotelRoomDetailsUseCase;

    @Override
    public void getHotelRoomDetails(HotelRoomRequest request, StreamObserver<HotelRoomDetails> response){
        HotelRoomDetailsDto details = getHotelRoomDetailsUseCase.details(request.getRoomId());

        RoomType protoRoomType = RoomType.valueOf(details.roomType().name());

        HotelRoomDetails roomDetails = HotelRoomDetails.newBuilder()
                .setRoomType(protoRoomType)
                .setCapacity(details.capacity())
                .setSerialNumber(details.serialNumber())
                .setPrice(details.price().toString())
                .build();

        response.onNext(roomDetails);
        response.onCompleted();
    }
}

