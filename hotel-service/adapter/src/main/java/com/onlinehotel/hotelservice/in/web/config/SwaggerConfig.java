package com.onlinehotel.hotelservice.in.web.config;

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
                .info(new Info().title("HoN Core Auth API")
                        .description("Authentication & authorization API")
                        .version(appVersion)
                        .license(new io.swagger.v3.oas.models.info.License().name("(C) HoN")));
    }

    @Bean
    public GroupedOpenApi hotelApi() {
        return GroupedOpenApi.builder()
                .group("hotels")
                .pathsToMatch("/api/hotel/**")
                .packagesToScan("com.onlinehotel.hotelservice.in.web")  // Your controller package
                .build();
    }
}



