package com.stock_microservice.stock_microservice.domain.api;

import com.stock_microservice.stock_microservice.domain.model.Product;

public interface IProductServicePort {
    void saveProduct(Product product);
}
