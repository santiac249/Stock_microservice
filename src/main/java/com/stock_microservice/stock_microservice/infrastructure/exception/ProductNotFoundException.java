package com.stock_microservice.stock_microservice.infrastructure.exception;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException(String message) {
    super(message);
  }
  public ProductNotFoundException() {
    super();
  }
}
