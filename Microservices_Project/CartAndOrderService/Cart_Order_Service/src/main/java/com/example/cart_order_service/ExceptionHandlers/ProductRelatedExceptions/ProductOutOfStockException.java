package com.example.cart_order_service.ExceptionHandlers.ProductRelatedExceptions;

public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException(String message) {
        super(message);
    }
}
