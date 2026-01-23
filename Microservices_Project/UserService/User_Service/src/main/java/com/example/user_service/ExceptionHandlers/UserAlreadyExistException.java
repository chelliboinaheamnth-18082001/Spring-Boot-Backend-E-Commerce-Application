package com.example.user_service.ExceptionHandlers;

public class UserAlreadyExistException extends RuntimeException
{
    public UserAlreadyExistException(String message)
    {
        super(message);
    }
}
