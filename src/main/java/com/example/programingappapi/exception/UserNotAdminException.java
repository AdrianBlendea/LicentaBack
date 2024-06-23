package com.example.programingappapi.exception;

public class UserNotAdminException extends RuntimeException{
  public UserNotAdminException(String message){super(message);}

}
