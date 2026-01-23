package com.example.product_service.ExceptionHandlers.GlobalExceptionHandler;



import com.example.product_service.ExceptionHandlers.ProductAlreadyExistsException;
import com.example.product_service.ExceptionHandlers.ProductNotFoundException;
import com.example.product_service.Product_DTOs.ErrorsDtos.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ProductsGlobalExceptionHandler {


    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleProductAlreadyExistException(ProductAlreadyExistsException e,
                                                                    HttpServletRequest webRequest) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message(e.getMessage())
                .path(webRequest.getRequestURI())
                .method(webRequest.getMethod())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(errorDTO);

    }
@ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleProductNotFoundException(ProductNotFoundException e,
                                                                   HttpServletRequest webRequest) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message(e.getMessage())
                .path(webRequest.getRequestURI())
                .method(webRequest.getMethod())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(errorDTO);

    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException e,
                                                            HttpServletRequest webRequest) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message(e.getMessage())
                .path(webRequest.getRequestURI())
                .method(webRequest.getMethod())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return ResponseEntity.badRequest().body(errorDTO);
    }
}
