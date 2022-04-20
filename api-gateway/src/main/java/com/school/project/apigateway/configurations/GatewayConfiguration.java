package com.school.project.apigateway.configurations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("auth-service", route -> route.path("/login/**", "/user/**").uri("lb://auth-service"))
                .route("create-service", route -> route.path("/create/**").uri("lb://create-service"))
                .route("delete-service", route -> route.path("/delete/**").uri("lb://delete-service"))
                .route("read-service", route -> route.path("/read/**").uri("lb://read-service"))
                .route("update-service", route -> route.path("/update/**").uri("lb://update-service"))
                .build();
    }
}
