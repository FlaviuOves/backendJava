package com.backend.backend.exception.user;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message)
    {
        super(message);
    }
}
