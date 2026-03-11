package com.onlinehotel.bookingservice;

import com.onlinehotel.hotelservice.adapter.in.grpc.HotelRoomServiceGrpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.grpc.client.ImportGrpcClients;

@SpringBootApplication
@EnableDiscoveryClient
public class Launcher {
    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
