package com.example.monolithicbackend.ExceptionHandling.OrdersExceptionHandling;

public class OrderUserNotFoundException extends RuntimeException{
    public OrderUserNotFoundException(String message) {
        super(message);
    }
}
