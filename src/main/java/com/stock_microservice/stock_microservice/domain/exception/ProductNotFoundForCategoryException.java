package com.stock_microservice.stock_microservice.domain.exception;

public class ProductNotFoundForCategoryException extends RuntimeException {
    public ProductNotFoundForCategoryException(String message) {
        super(message);
    }
}
