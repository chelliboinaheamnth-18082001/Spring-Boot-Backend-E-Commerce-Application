package com.example.user_service.ExceptionHandlers.GlobalExceptionHandler;

import com.example.user_service.ExceptionHandlers.UserAlreadyExistException;
import com.example.user_service.ExceptionHandlers.UserNotFoundException;
import com.example.user_service.User_DTOs.ErrorsDtos.ErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UsersGlobalExceptionHandler {


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorDTO> handleUserAlreadyExistException(UserAlreadyExistException e,
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleUserNotFoundException(UserNotFoundException e,
                                                                HttpServletRequest webRequest) {
        ErrorDTO errorDTO = ErrorDTO.builder()
                .timestamp(String.valueOf(LocalDateTime.now()))
                .message(e.getMessage())
                .path(webRequest.getRequestURI())
                .method(webRequest.getMethod())
                .status(HttpStatus.NOT_FOUND.value())
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
