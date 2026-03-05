package com.onlinehotel.bookingservice.adapter.config;

import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.grpc.client.ImportGrpcClients;

@Configuration
@ImportGrpcClients(target = "hotel-service")
public class GrpcConfig {
    @Bean
    HotelRoomServiceGrpc.HotelRoomServiceBlockingStub hotelRoomServiceStub(
            GrpcChannelFactory channelFactory) {
        return HotelRoomServiceGrpc.newBlockingStub(
                channelFactory.createChannel("hotel-service"));
    }

}
