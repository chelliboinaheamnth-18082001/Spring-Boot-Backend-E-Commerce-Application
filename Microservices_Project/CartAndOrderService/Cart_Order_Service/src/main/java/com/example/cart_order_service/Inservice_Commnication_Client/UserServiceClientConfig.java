package com.example.cart_order_service.Inservice_Commnication_Client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class UserServiceClientConfig {



    @Bean
    public UserServiceClientInterface userServiceClient(RestClient.Builder builder) {

        RestClient restClient = builder
                .baseUrl("http://UserService")
                .defaultStatusHandler(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            throw new ResponseStatusException(
                                    response.getStatusCode(),
                                    "Invalid User Id"
                            );
                        }
                )
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        return HttpServiceProxyFactory
                .builderFor(adapter)
                .build()
                .createClient(UserServiceClientInterface.class);
    }
}
