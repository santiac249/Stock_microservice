package com.stock_microservice.stock_microservice.domain.exception;

public class DuplicateBrandNameException extends RuntimeException {
    public DuplicateBrandNameException(String message) {
        super(message);
    }
}
