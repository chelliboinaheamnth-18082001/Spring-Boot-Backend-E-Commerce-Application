package com.example.monolithicbackend.ExceptionHandling.ProductsRelatedExceptionHandler;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(String message)
    {
        super(message);
    }
}
