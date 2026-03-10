package com.onlinehotel.hotelservice.adapter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        String appVersion = "1.0.0";
        return new OpenAPI()
                .info(new Info().title("OnlineHotel Core Book API")
                        .version(appVersion)
                        .description("Payment API")
                        .license(new io.swagger.v3.oas.models.info.License().name("(C) OnlineHotel")));
    }

    @Bean
    public GroupedOpenApi paymentApi() {
        return GroupedOpenApi.builder()
                .group("payment-service")
                .pathsToMatch("/api/payment/**")
                .packagesToScan("com.onlinehotel.hotelservice.adapter.in.rest")
                .build();
    }
}
