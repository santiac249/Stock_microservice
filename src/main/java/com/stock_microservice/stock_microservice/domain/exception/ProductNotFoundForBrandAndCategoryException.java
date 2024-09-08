package com.stock_microservice.stock_microservice.domain.exception;

public class ProductNotFoundForBrandAndCategoryException extends RuntimeException {
  public ProductNotFoundForBrandAndCategoryException(String message) {
    super(message);
  }
}
