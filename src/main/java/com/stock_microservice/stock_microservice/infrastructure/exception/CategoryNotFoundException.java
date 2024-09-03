package com.stock_microservice.stock_microservice.infrastructure.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(String message){
        super(message);
    }

    public CategoryNotFoundException(){
        super();
    }
}