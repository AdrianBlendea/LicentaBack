package com.example.programingappapi.exception;

public class AccountNotEnabledException extends RuntimeException{

    public AccountNotEnabledException(String message)
    {
        super(message);
    }
}
