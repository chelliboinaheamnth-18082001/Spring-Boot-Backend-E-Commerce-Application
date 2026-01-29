package com.example.cart_order_service.Inservice_Commnication_Client;

import com.example.cart_order_service.DTOs.ProductsDTOs.ProductResponseDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClientInterface {

    @GetExchange("/api/products/{id}")
    public  ProductResponseDTO getProductById(@PathVariable Long id);

}
