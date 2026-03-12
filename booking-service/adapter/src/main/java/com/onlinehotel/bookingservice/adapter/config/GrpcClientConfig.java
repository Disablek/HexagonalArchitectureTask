package com.onlinehotel.bookingservice.adapter.config;

import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import io.grpc.ManagedChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel managedChannel(GrpcChannelFactory grpcChannelFactory) {
        return grpcChannelFactory.createChannel("hotel-service");
    }

    @Bean
    public HotelRoomServiceGrpc.HotelRoomServiceBlockingStub hotelRoomServiceBlockingStub(
            GrpcChannelFactory grpcChannelFactory) {
        return HotelRoomServiceGrpc.newBlockingStub(managedChannel(grpcChannelFactory));
    }
}

