package com.example.retail_rocket.ExceptionHandler;

public class ExceptionOccured extends RuntimeException{
    public ExceptionOccured(String exceptionMesssage){
        super(exceptionMesssage);
    }
}
