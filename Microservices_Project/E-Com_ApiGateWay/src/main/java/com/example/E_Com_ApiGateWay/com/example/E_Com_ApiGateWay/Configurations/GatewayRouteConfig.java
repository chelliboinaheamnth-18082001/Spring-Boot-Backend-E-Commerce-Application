package com.example.E_Com_ApiGateWay.Configurations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                // Eureka Server Route
                .route("eureka_server", r -> r
                        .path("/eureka/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("http://localhost:8761"))

                // User Service Route
                .route("user_service", r -> r
                        .path("/api/users", "/api/users/**")
                        .uri("lb://USERSERVICE"))

                // Product Service Route
                .route("product_service", r -> r
                        .path("/api/products/**")
                        .filters(f -> f.circuitBreaker
                                (c -> c.setName("ECommCircuitBreaker")
                                .setFallbackUri("forward:/product-service-fallback")))
                        .uri("lb://PRODUCTSERVICE"))

                // Cart + Order Service Route
                .route("cart_order_service", r -> r
                        .path("/api/cart-items/**", "/api/order/**")
                        .uri("lb://CARTORDERSERVICE"))

                .build();
    }
}
