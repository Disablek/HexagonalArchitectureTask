package com.onlinehotel.bookingservice.adapter.mapper.grpc;

import com.onlinehotel.bookingservice.application.dto.HotelRoomDetailsDto;
import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomDetails;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-12T12:59:13+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.3.1.jar, environment: Java 21.0.3 (Oracle Corporation)"
)
@Component
public class HotelRoomGrpcMapperImpl implements HotelRoomGrpcMapper {

    @Override
    public HotelRoomDetailsDto toDto(HotelRoomDetails grpc) {
        if ( grpc == null ) {
            return null;
        }

        Long roomId = null;
        String roomType = null;
        Integer capacity = null;
        Integer serialNumber = null;
        BigDecimal price = null;

        roomId = grpc.getRoomId();
        if ( grpc.getRoomType() != null ) {
            roomType = grpc.getRoomType().name();
        }
        capacity = grpc.getCapacity();
        serialNumber = grpc.getSerialNumber();
        if ( grpc.getPrice() != null ) {
            price = new BigDecimal( grpc.getPrice() );
        }

        HotelRoomDetailsDto hotelRoomDetailsDto = new HotelRoomDetailsDto( roomId, roomType, capacity, serialNumber, price );

        return hotelRoomDetailsDto;
    }

    @Override
    public com.onlinehotel.bookingservice.adapter.out.grpc.HotelRoomDetails fromDto(HotelRoomDetailsDto grpc) {
        if ( grpc == null ) {
            return null;
        }

        com.onlinehotel.bookingservice.adapter.out.grpc.HotelRoomDetails.Builder hotelRoomDetails = com.onlinehotel.bookingservice.adapter.out.grpc.HotelRoomDetails.newBuilder();

        if ( grpc.roomId() != null ) {
            hotelRoomDetails.setRoomId( grpc.roomId() );
        }
        if ( grpc.serialNumber() != null ) {
            hotelRoomDetails.setSerialNumber( grpc.serialNumber() );
        }
        if ( grpc.capacity() != null ) {
            hotelRoomDetails.setCapacity( grpc.capacity() );
        }
        hotelRoomDetails.setRoomType( grpc.roomType() );
        if ( grpc.price() != null ) {
            hotelRoomDetails.setPrice( grpc.price().toString() );
        }

        return hotelRoomDetails.build();
    }
}
