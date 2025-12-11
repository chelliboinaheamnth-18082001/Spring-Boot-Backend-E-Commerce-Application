package com.example.monolithicbackend.ExceptionHandling.OrdersExceptionHandling;

import com.example.monolithicbackend.DTO.ErrorsDtos.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
public class OrdersGlobalExceptionHandling {

    @ExceptionHandler(OrderUserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleOrderUserNotFoundException(OrderUserNotFoundException ex,
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
