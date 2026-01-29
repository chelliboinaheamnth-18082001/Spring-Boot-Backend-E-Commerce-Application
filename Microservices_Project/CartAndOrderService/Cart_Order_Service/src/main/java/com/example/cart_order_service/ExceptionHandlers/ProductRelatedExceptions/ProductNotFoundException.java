package com.example.cart_order_service.ExceptionHandlers.ProductRelatedExceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
