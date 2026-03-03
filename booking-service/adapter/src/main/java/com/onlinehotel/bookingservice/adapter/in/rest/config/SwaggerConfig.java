package com.onlinehotel.bookingservice.adapter.in.rest.config;

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
                        .description("Booking API")
                        .license(new io.swagger.v3.oas.models.info.License().name("(C) OnlineHotel")));
    }

    @Bean
    public GroupedOpenApi hotelApi() {
        return GroupedOpenApi.builder()
                .group("booking-service")
                .pathsToMatch("/api/booking/**")
                .packagesToScan("com.onlinehotel.bookingservice.in.web")
                .build();
    }
}