package com.stock_microservice.stock_microservice.domain.spi;

import com.stock_microservice.stock_microservice.domain.model.Product;

public interface IProductPersistencePort {
    void saveProduct(Product product);
}
