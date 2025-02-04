package com.example.programingappapi.exception;

import com.example.programingappapi.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProblemNotFoundException.class)
    public ResponseEntity<ResponseDto> handleNotFoundException(ProblemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(ex.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseDto> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDto(ex.getMessage()));
    }
    @ExceptionHandler(PasswordAndEmailNotMatchingException.class)
    public final ResponseEntity<String> handlePasswordAndEmailNotMatchingException(
            PasswordAndEmailNotMatchingException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<String> handleUserNotFoundException(
            UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotAdminException.class)
    public final ResponseEntity<String> handleUserNotAdminException(
            UserNotAdminException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(AccountNotEnabledException.class)
    public final ResponseEntity<String> handleAccountNotEnabledException(
           AccountNotEnabledException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(SolutionNotFoundException.class)
    public final ResponseEntity<String> handleSolutionNotFoundException(
           SolutionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(TypeNotFoundException.class)
    public final ResponseEntity<String> handleTypeNotFoundException(
            TypeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NotEnoughSolutionsForReportException.class)
    public final ResponseEntity<String> handleNotEnoughSolutionForReportException(
            NotEnoughSolutionsForReportException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
