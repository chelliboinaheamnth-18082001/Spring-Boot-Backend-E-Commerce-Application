package com.example.monolithicbackend.ExceptionHandling.CartExceptionHandler.GlobalExceptionHandler;

import com.example.monolithicbackend.DTO.ErrorsDtos.ErrorDTO;
import com.example.monolithicbackend.ExceptionHandling.CartExceptionHandler.CartItemNotFoundException;
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

}
