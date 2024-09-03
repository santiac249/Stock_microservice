package com.stock_microservice.stock_microservice.domain.exception;

public class CategoryValidationException extends RuntimeException {
    public CategoryValidationException(String message) {
        super(message);
    }
}
