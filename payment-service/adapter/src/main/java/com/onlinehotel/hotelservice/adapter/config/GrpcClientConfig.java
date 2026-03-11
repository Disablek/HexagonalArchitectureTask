package com.onlinehotel.hotelservice.adapter.config;

import com.onlinehotel.bookingservice.adapter.out.grpc.BookingServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

@Configuration
public class GrpcClientConfig {

    @Bean
    public BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub(
            GrpcChannelFactory grpcChannelFactory) {

        return BookingServiceGrpc.newBlockingStub(
                grpcChannelFactory.createChannel("booking-service")
        );
    }
}



