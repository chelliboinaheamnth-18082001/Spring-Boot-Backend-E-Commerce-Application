package com.example.E_Com_ApiGateWay.LoggingPackage.LoggingPackage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilterTraces implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilterTraces.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Request Method : {}", exchange.getRequest().getMethod());
        logger.info("Request URI : {}", exchange.getRequest().getURI());
        logger.info("Request Headers : {}", exchange.getRequest().getHeaders());
        logger.info("Request Body : {}", exchange.getRequest().getBody());
        return chain.filter(exchange);
    }
}
