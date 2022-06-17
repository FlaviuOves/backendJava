package com.backend.backend.exception.user;

public class RoleAlreadyExistException extends RuntimeException{
    public RoleAlreadyExistException(String message)
    {
        super(message);
    }
}
