package com.example.monolithicbackend.ExceptionHandling.CartExceptionHandler;

public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
