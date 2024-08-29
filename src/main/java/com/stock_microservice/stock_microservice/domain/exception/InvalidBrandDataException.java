package com.stock_microservice.stock_microservice.domain.exception;

public class InvalidBrandDataException extends RuntimeException {
    public InvalidBrandDataException(String message) {
        super(message);
    }
}
