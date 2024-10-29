package com.example.retail_rocket.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ExceptionOccured.class)
    ResponseEntity<ExceptionResponse> handleOrderNotFoundException(ExceptionOccured notFoundException){
        return new ResponseEntity(new ExceptionResponse(notFoundException.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Tokenvalidation.class)
    ExceptionResponse handleOrderNotFoundException(Tokenvalidation tokenvalidation){
        return new ExceptionResponse(tokenvalidation.getMessage());
    }
}
