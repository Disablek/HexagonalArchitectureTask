package com.onlinehotel.bookingservice.adapter.config;

import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Value("${spring.grpc.hotel-service.address}")
    private String address;

    @Bean(destroyMethod = "shutdown")
    public ManagedChannel hotelServiceChannel() {
        return ManagedChannelBuilder.forTarget(address)
                .usePlaintext()
                .build();
    }

    @Bean
    public HotelRoomServiceGrpc.HotelRoomServiceBlockingStub hotelRoomServiceBlockingStub(
            ManagedChannel hotelServiceChannel) {
        return HotelRoomServiceGrpc.newBlockingStub(hotelServiceChannel);
    }
}



