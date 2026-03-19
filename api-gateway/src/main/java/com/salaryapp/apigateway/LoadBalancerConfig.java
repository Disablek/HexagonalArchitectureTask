package com.salaryapp.apigateway;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@LoadBalancerClients({
        @LoadBalancerClient(value = "HOTEL-SERVICE", configuration = CustomLoadBalancerConfig.class),
        @LoadBalancerClient(value = "BOOKING-SERVICE", configuration = CustomLoadBalancerConfig.class)
})
public class LoadBalancerConfig {
}

