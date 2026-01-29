package com.example.cart_order_service.Inservice_Commnication_Client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class ProductServiceClientConfig {




    @Bean
    public ProductServiceClientInterface productServiceClient(RestClient.Builder builder) {

        RestClient restClient = builder
                .baseUrl("http://productService")
                .defaultStatusHandler(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            throw new ResponseStatusException(
                                    response.getStatusCode(),
                                    "Invalid Product Id"
                            );
                        }
                )
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        return HttpServiceProxyFactory
                .builderFor(adapter)
                .build()
                .createClient(ProductServiceClientInterface.class);
    }
}
