package com.onlinehotel.hotelservice.adapter.config.grpc_eureka;


import com.netflix.discovery.EurekaClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class GrpcEurekaConfiguration {

    @Bean
    public EurekaNameResolverProviderInitializer resolverInitializer(EurekaClient eurekaClient) {
        return new EurekaNameResolverProviderInitializer(eurekaClient);
    }
}


@Component
class EurekaNameResolverProviderInitializer {
    @Value("${grpc.client.port-metadata:grpc.port}")
    private String portMetaData;

    private final EurekaClient eurekaClient;

    public EurekaNameResolverProviderInitializer(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    @PostConstruct
    public void init() {
        System.out.println("+++++++++++++++++++ portMetaData = '" + portMetaData + "'");
        EurekaNameResolverProvider.setEurekaClient(eurekaClient);
        EurekaNameResolverProvider.setPortMetaData(portMetaData);
    }
}









