package com.stock_microservice.stock_microservice.domain.exception;

public class InvalidCategoryDataException extends RuntimeException{
    public InvalidCategoryDataException(String message) {
        super(message);
    }
}