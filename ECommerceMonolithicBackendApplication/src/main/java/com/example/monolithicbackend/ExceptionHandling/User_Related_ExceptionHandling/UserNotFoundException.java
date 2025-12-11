package com.example.monolithicbackend.ExceptionHandling.User_Related_ExceptionHandling;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String message)
    {
        super(message);
    }
}
