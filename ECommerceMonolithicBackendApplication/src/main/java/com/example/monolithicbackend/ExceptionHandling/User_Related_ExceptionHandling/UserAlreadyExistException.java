package com.example.monolithicbackend.ExceptionHandling.User_Related_ExceptionHandling;

public class UserAlreadyExistException extends RuntimeException
{
    public UserAlreadyExistException(String message)
    {
        super(message);
    }
}
