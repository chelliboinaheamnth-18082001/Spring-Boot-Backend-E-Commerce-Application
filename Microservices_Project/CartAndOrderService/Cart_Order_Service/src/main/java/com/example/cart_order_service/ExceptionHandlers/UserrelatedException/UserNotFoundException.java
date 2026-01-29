package com.example.cart_order_service.ExceptionHandlers.UserrelatedException;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
