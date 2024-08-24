package com.stock_microservice.stock_microservice.domain.exception;

public class DuplicateCategoryNameException extends RuntimeException{
    public DuplicateCategoryNameException(String message) {
        super(message);
    }
}