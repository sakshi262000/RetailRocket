package com.example.retail_rocket.ExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ExceptionOccured.class)
    ExceptionResponse handleOrderNotFoundException(ExceptionOccured notFoundException){
        return new ExceptionResponse(notFoundException.getMessage());
    }

    @ExceptionHandler(Tokenvalidation.class)
    ExceptionResponse handleOrderNotFoundException(Tokenvalidation tokenvalidation){
        return new ExceptionResponse(tokenvalidation.getMessage());
    }
}
