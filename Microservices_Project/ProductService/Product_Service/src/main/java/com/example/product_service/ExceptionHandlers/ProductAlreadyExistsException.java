package com.example.product_service.ExceptionHandlers;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String message)
    {
        super(message);
    }
}
