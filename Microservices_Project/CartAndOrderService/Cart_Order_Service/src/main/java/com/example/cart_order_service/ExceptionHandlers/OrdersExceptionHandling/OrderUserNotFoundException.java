package com.example.cart_order_service.ExceptionHandlers.OrdersExceptionHandling;

public class OrderUserNotFoundException extends RuntimeException{
    public OrderUserNotFoundException(String message) {
        super(message);
    }
}
