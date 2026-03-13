package com.onlinehotel.bookingservice.adapter.config;

import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcClientConfig {

    @Bean
    public HotelRoomServiceGrpc.HotelRoomServiceBlockingStub blockingStub(
            GrpcChannelFactory grpcChannelFactory) {

        return HotelRoomServiceGrpc.newBlockingStub(
                grpcChannelFactory.createChannel("hotel-service")
        );
    }
}

