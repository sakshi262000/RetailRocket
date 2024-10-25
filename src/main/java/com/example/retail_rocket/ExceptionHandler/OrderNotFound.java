package com.example.retail_rocket.ExceptionHandler;

public class OrderNotFound extends RuntimeException{
    public OrderNotFound(String exceptionMesssage){
        super(exceptionMesssage);
    }
}
