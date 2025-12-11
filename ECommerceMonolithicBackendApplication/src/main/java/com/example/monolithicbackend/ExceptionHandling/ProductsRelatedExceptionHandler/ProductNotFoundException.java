package com.example.monolithicbackend.ExceptionHandling.ProductsRelatedExceptionHandler;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
