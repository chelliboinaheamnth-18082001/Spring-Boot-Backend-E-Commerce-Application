package com.example.cart_order_service.Inservice_Commnication_Client;

import com.example.cart_order_service.DTOs.UserDTOs.UserResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface UserServiceClientInterface {

    @GetExchange("api/users/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id);

}
