package com.example.cart_order_service.ExceptionHandlers.CartExceptionHandler;

public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
