package com.stock_microservice.stock_microservice.application.handler;

import com.stock_microservice.stock_microservice.application.dto.ProductRequest;

public interface IProductHandler {
    void saveProduct(ProductRequest productRequest);
}
