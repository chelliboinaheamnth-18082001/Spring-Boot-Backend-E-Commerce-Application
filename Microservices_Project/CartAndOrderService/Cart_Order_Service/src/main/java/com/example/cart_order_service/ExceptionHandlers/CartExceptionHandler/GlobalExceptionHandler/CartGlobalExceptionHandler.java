package com.example.cart_order_service.ExceptionHandlers.CartExceptionHandler.GlobalExceptionHandler;


import com.example.cart_order_service.DTOs.ErrorsDtos.ErrorDTO;
import com.example.cart_order_service.ExceptionHandlers.CartExceptionHandler.CartItemNotFoundException;
import com.example.cart_order_service.ExceptionHandlers.ProductRelatedExceptions.ProductNotFoundException;
import com.example.cart_order_service.ExceptionHandlers.ProductRelatedExceptions.ProductOutOfStockException;
import com.example.cart_order_service.ExceptionHandlers.UserrelatedException.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CartGlobalExceptionHandler {

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCartItemNotFoundException(CartItemNotFoundException ex,
                                                                    HttpServletRequest webRequest) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message(ex.getMessage())
                .path(webRequest.getRequestURI())
                .method(webRequest.getMethod())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(errorDTO);



    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(
            ProductNotFoundException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.badRequest().body(errorDTO);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(
            UserNotFoundException ex,
            HttpServletRequest request) {

        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.badRequest().body(errorDTO);
    }



    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<ErrorDTO> handleProductOutOfStockException(ProductOutOfStockException ex,
                                                                   HttpServletRequest webRequest) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message(ex.getMessage())
                .path(webRequest.getRequestURI())
                .method(webRequest.getMethod())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(errorDTO);



    }

}
