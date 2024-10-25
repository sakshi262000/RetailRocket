package com.example.retail_rocket.ExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(OrderNotFound.class)
    ExceptionResponse handleOrderNotFoundException(OrderNotFound notFoundException){
return new ExceptionResponse(notFoundException.getMessage());
    }
}
