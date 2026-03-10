package com.onlinehotel.hotelservice.adapter.config;

import com.onlinehotel.bookingservice.adapter.out.grpc.BookingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Value("${spring.grpc.booking-service.address}")
    private String address;

    @Bean(destroyMethod = "shutdown")
    public ManagedChannel bookingServiceChannel() {
        return ManagedChannelBuilder.forTarget(address)
                .usePlaintext()
                .build();
    }

    @Bean
    public BookingServiceGrpc.BookingServiceBlockingStub bookingServiceBlockingStub(
            ManagedChannel hotelServiceChannel) {
        return BookingServiceGrpc.newBlockingStub(hotelServiceChannel);
    }
}

