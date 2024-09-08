package com.stock_microservice.stock_microservice.domain.exception;

public class ProductNotFoundForBrandException extends RuntimeException {
    public ProductNotFoundForBrandException(String message) {
        super(message);
    }
}
