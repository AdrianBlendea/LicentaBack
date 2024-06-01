package com.example.programingappapi.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message)
    {
        super(message);
    }

}
