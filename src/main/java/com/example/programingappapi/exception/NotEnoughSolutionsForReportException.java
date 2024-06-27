package com.example.programingappapi.exception;

public class NotEnoughSolutionsForReportException extends RuntimeException{

    public NotEnoughSolutionsForReportException(String message)
    {
        super(message);
    }
}
